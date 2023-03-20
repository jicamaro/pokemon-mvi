package com.accenture.mvi.data.model

import com.accenture.mvi.presentation.model.Sprite
import com.google.gson.annotations.SerializedName

data class SpriteEntity(@SerializedName("front_default") val frontDefault: String,
                        val other: OtherSpriteEntity) {

    fun toSprite() = Sprite(frontDefault = frontDefault, other = other.toOtherSprite())
}