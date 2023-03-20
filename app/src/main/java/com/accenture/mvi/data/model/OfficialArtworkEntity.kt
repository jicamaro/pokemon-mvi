package com.accenture.mvi.data.model

import com.accenture.mvi.presentation.model.OfficialArtwork
import com.google.gson.annotations.SerializedName

data class OfficialArtworkEntity(@SerializedName("front_default") val frontDefault: String) {

    fun toOfficialArtwork() = OfficialArtwork(frontDefault = frontDefault)
}