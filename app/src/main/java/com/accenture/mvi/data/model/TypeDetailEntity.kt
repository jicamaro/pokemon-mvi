package com.accenture.mvi.data.model

import com.accenture.mvi.presentation.model.TypeDetail

data class TypeDetailEntity(private val name: String,
                            private val url: String) {

    fun toTypeDetail() = TypeDetail(name = name)
}