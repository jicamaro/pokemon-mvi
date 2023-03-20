package com.accenture.mvi.presentation

import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.accenture.mvi.domain.LoadListWithDetailUseCase
import com.accenture.mvi.presentation.model.Pokemon
import com.accenture.mvi.presentation.mvi.ListScreenIntent
import com.accenture.mvi.presentation.mvi.ListScreenView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.URL

class ListViewModel(private val loadListUseCase: LoadListWithDetailUseCase): ViewModel() {

    private val intentProcessor: MutableStateFlow<ListScreenIntent> = MutableStateFlow(ListScreenIntent.LoadContent)
    private val processor = intentProcessor.asStateFlow()

    private val _uiState = MutableStateFlow<ListScreenView>(ListScreenView.Started)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            processor.collectLatest { intent ->
                when (intent) {
                    is ListScreenIntent.LoadContent -> {
                        _uiState.emit(ListScreenView.ShowLoading)
                        loadContent()
                    }
                    is ListScreenIntent.GoToDetail -> {
                        _uiState.emit(ListScreenView.NavigateToDetail(intent.pokemon))
                    }
                    is ListScreenIntent.Retry -> {
                        _uiState.emit(ListScreenView.ShowLoading)
                        intent(ListScreenIntent.LoadContent)
                    }
                    is ListScreenIntent.Back -> _uiState.emit(ListScreenView.NavigateBack)
                    is ListScreenIntent.Finish -> _uiState.emit(ListScreenView.Ended)
                }
            }
        }
    }

    private fun loadContent() {
        viewModelScope.launch(Dispatchers.IO) {
            loadListUseCase.execute().map { result ->
                when {
                    result.isSuccess -> {
                        val entities = result.getOrNull() ?: listOf()
                        val list = entities.map {
                            with (it.toPokemon()) {
                                val bitmap = BitmapFactory.decodeStream(URL(detail().sprites().other.sprite.frontDefault)
                                    .openConnection().getInputStream())

                                val palette = Palette.from(bitmap).generate()

                                val backgroundColor = palette.lightMutedSwatch?.rgb ?: android.graphics.Color.WHITE
                                val textColor = palette.darkMutedSwatch?.rgb ?: android.graphics.Color.WHITE
                                val cardBackgroundColor = palette.lightVibrantSwatch?.rgb ?: android.graphics.Color.WHITE
                                val topBarColor = palette.mutedSwatch?.rgb ?: android.graphics.Color.BLACK

                                bitmap.recycle()
                                copy(backgroundColor = backgroundColor,
                                    textColor = textColor,
                                    topBarColor = topBarColor,
                                    cardBackgroundColor = cardBackgroundColor)
                            }
                        }

                        _uiState.emit(ListScreenView.ShowContent(list))
                    }
                    result.isFailure -> {
                        val data = result.exceptionOrNull() ?: NullPointerException()
                        _uiState.emit(ListScreenView.ShowError(data))
                    }
                }
            }.stateIn(this)
        }
    }

    fun intent(intent: ListScreenIntent) {
        viewModelScope.launch(Dispatchers.IO) {
            intentProcessor.emit(intent)
        }
    }
}