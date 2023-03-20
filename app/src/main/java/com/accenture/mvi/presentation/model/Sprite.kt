package com.accenture.mvi.presentation.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sprite(@SerializedName("front_default") val frontDefault: String,
                  val other: OtherSprite): Parcelable