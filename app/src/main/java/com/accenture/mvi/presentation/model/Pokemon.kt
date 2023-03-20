package com.accenture.mvi.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(private val name: String = "",
                   private val detail: Detail? = null,
                   val backgroundColor: Int = 0,
                   val cardBackgroundColor: Int = 0,
                   val topBarColor: Int = 0,
                   val textColor: Int = 0): Parcelable {

    fun name() = name

    fun detail() = detail ?: throw NullPointerException()
}