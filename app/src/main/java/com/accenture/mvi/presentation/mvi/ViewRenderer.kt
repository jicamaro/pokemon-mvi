package com.accenture.mvi.presentation.mvi

interface ViewRenderer<T> {

    fun render(view: T)
}