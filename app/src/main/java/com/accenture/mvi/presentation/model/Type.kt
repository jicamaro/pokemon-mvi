package com.accenture.mvi.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Type(private val slot: Int,
                val type: TypeDetail): Parcelable