package com.accenture.mvi.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

abstract class UseCase<T, V>(
    private val job: Job = Job(),
    private val coroutineDispatcher: CoroutineContext = Dispatchers.IO,
    private val coroutineScope: CoroutineScope = CoroutineScope(coroutineDispatcher + job)
) {

    protected abstract suspend fun executeOnBackground(parameters: T? = null): V

    suspend fun execute(parameters: T? = null) = flow<Result<V>> {
        runCatching {
            executeOnBackground(parameters)
        }.onSuccess {
            emit(Result.success(it))
        }.onFailure {
            emit(Result.failure(it))
        }
    }.flowOn(coroutineDispatcher)

    fun cancel() {
        job.cancel()
    }
}