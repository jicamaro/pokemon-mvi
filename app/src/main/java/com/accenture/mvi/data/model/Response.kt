package com.accenture.mvi.data.model

data class Response<V>(val count: Int,
                       val next: String,
                       val previous: String? = null,
                       val results: V)