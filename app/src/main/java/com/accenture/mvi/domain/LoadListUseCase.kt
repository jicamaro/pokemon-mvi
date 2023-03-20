package com.accenture.mvi.domain

import com.accenture.mvi.data.Repository
import com.accenture.mvi.data.model.PokemonEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadListUseCase(private val repository: Repository): UseCase<Any, List<PokemonEntity>>() {

    override suspend fun executeOnBackground(parameters: Any?) = repository.getList()
}