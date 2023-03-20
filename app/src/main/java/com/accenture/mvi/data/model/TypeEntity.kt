package com.accenture.mvi.data.model

import com.accenture.mvi.presentation.model.Type

data class TypeEntity(private val slot: Int,
                      private val type: TypeDetailEntity) {

    fun toType() = Type(slot = slot, type = type.toTypeDetail())
}