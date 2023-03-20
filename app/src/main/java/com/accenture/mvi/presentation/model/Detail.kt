package com.accenture.mvi.presentation.model

import android.os.Parcelable
import com.accenture.mvi.data.model.SpriteEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Detail(private val height: Int,
                  private val weight: Int,
                  private val id: Int,
                  private val name: String,
                  private val types: List<Type> = listOf(),
                  private val moves: List<Move> = listOf(),
                  private val sprites: Sprite
): Parcelable {

    fun name() = name

    fun sprites() = sprites

    fun types() = types

    fun height() = height

    fun weight() = weight
}