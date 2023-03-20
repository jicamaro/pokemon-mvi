package com.accenture.mvi.presentation.mvi

import com.accenture.mvi.presentation.model.Pokemon

sealed class DetailScreenView {

    object ShowLoading: DetailScreenView()
    class ShowContent(val pokemon: Pokemon) : DetailScreenView()
    class ShowError(val throwable: Throwable): DetailScreenView()
    object NavigateBack: DetailScreenView()
    object Finish: DetailScreenView()
}