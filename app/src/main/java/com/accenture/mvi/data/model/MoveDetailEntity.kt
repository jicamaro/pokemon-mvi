package com.accenture.mvi.data.model

import com.accenture.mvi.presentation.model.MoveDetail

data class MoveDetailEntity(private val name: String,
                            private val url: String) {

    fun toMoveDetail() = MoveDetail(name = name)
}