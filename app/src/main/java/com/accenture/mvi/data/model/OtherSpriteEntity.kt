package com.accenture.mvi.data.model

import com.accenture.mvi.presentation.model.OtherSprite
import com.google.gson.annotations.SerializedName

class OtherSpriteEntity(@SerializedName("official-artwork") val spriteEntity: OfficialArtworkEntity) {

    fun toOtherSprite(): OtherSprite = OtherSprite(sprite = spriteEntity.toOfficialArtwork())
}