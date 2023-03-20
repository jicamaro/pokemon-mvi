package com.accenture.mvi.presentation.mvi

import com.accenture.mvi.presentation.model.Pokemon

sealed class ListScreenIntent {

    object LoadContent : ListScreenIntent()
    object Retry : ListScreenIntent()
    class GoToDetail(val pokemon: Pokemon): ListScreenIntent()
    object Back: ListScreenIntent()
    object Finish: ListScreenIntent()
}