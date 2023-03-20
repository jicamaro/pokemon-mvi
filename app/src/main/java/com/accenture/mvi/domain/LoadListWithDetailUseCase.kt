package com.accenture.mvi.domain

import com.accenture.mvi.data.Repository
import com.accenture.mvi.data.model.PokemonEntity

class LoadListWithDetailUseCase(private val repository: Repository): UseCase<Any, List<PokemonEntity>>() {

    override suspend fun executeOnBackground(parameters: Any?) = repository.getListWithDetail()
}