package com.accenture.mvi.data.model

import com.accenture.mvi.presentation.model.Move

data class MoveEntity(private val move: MoveDetailEntity) {

    fun toMove() = Move(move = move.toMoveDetail())
}