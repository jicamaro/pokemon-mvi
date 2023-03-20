package com.accenture.mvi.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.accenture.mvi.presentation.mvi.DetailScreenIntent
import com.accenture.mvi.presentation.mvi.DetailScreenView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailViewModel: ViewModel() {

    private val intentProcessor: MutableStateFlow<DetailScreenIntent> =
        MutableStateFlow(DetailScreenIntent.Empty)
    private val processor = intentProcessor.asStateFlow()

    private val _uiState = MutableStateFlow<DetailScreenView>(DetailScreenView.ShowLoading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            processor.collectLatest { intent ->
                when (intent) {
                    is DetailScreenIntent.LoadContent -> _uiState.emit(DetailScreenView.ShowContent(intent.pokemon))
                    is DetailScreenIntent.Retry -> { }
                    is DetailScreenIntent.Back -> _uiState.emit(DetailScreenView.NavigateBack)
                    is DetailScreenIntent.Finish -> _uiState.emit(DetailScreenView.Finish)
                    is DetailScreenIntent.Empty -> { }
                }
            }
        }
    }

    fun intent(intent: DetailScreenIntent) {
        viewModelScope.launch(Dispatchers.IO) {
            intentProcessor.emit(intent)
        }
    }
}