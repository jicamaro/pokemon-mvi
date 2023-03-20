package com.accenture.mvi.presentation.mvi

import com.accenture.mvi.presentation.model.Pokemon

sealed class ListScreenView {

    object Started: ListScreenView()
    object ShowLoading: ListScreenView()
    class ShowContent(val items: List<Pokemon>): ListScreenView()
    class ShowError(val throwable: Throwable): ListScreenView()
    class NavigateToDetail(val pokemon: Pokemon): ListScreenView()
    object NavigateBack: ListScreenView()
    object Ended: ListScreenView()
}