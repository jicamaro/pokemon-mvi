package com.accenture.mvi.data

import com.accenture.mvi.data.model.DetailEntity
import com.accenture.mvi.data.model.PokemonEntity
import com.accenture.mvi.data.model.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface PokemonAPI {

    @Headers("Content-Type: application/json")
    @GET("pokemon")
    suspend fun getPokemonList(): Response<List<PokemonEntity>>

    @Headers("Content-Type: application/json")
    @GET("pokemon/{id}?limit=40")
    suspend fun getPokemonDetail(@Path("id") id: String): DetailEntity
}