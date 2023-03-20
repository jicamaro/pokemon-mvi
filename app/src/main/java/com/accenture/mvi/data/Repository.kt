package com.accenture.mvi.data

import android.util.Log
import com.accenture.mvi.data.model.PokemonEntity

class Repository(private val webService: PokemonAPI) {

    suspend fun getList(): List<PokemonEntity> = webService.getPokemonList().results

    suspend fun getListWithDetail(): List<PokemonEntity> {
        return runCatching {
            val result = webService.getPokemonList().results
            result.map {
                val detail = webService.getPokemonDetail(it.id())
                it.copy(detail = detail)
            }
        }.onFailure { Log.e("Error", it.message.orEmpty()) }.getOrThrow()
    }

    suspend fun getDetail(id: String) = webService.getPokemonDetail(id)
}