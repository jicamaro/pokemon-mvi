package com.accenture.mvi.data.model

import com.accenture.mvi.presentation.model.Detail

data class DetailEntity(private val height: Int,
                        private val weight: Int,
                        private val id: Int,
                        private val name: String,
                        private val types: List<TypeEntity>,
                        private val moves: List<MoveEntity>,
                        private val sprites: SpriteEntity) {

    fun toDetail() = Detail(
        height = height,
        weight = weight,
        id = id,
        name = name,
        types = types.map { it.toType() },
        moves = moves.map { it.toMove() },
        sprites = sprites.toSprite()
    )
}