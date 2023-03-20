package com.accenture.mvi.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Move(private val move: MoveDetail): Parcelable