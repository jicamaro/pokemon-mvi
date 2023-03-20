package com.accenture.mvi.presentation.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class OtherSprite(@SerializedName("official-artwork") val sprite: OfficialArtwork): Parcelable