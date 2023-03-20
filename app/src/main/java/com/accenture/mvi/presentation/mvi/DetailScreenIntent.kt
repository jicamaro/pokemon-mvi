package com.accenture.mvi.presentation.mvi

import com.accenture.mvi.presentation.model.Pokemon

sealed class DetailScreenIntent {

    class LoadContent(val pokemon: Pokemon) : DetailScreenIntent()
    object Retry : DetailScreenIntent()
    object Back: DetailScreenIntent()
    object Finish: DetailScreenIntent()
    object Empty: DetailScreenIntent()
}