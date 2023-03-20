package com.accenture.mvi.data.model

import com.accenture.mvi.presentation.model.Pokemon

private const val idPattern: String = "\\/(\\d+)\\/$"

data class PokemonEntity(private val name: String,
                         private val url: String,
                         private val detail: DetailEntity? = null) {

    fun id(): String = with(idPattern.toRegex()) {
        find(url)?.groupValues?.get(1) ?: throw NoSuchElementException()
    }

    fun name() = name

    fun detail() = detail ?: throw NullPointerException()

    fun toPokemon() = Pokemon(name = name, detail = detail?.toDetail())
}