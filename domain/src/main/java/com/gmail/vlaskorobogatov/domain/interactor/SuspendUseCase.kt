package com.gmail.vlaskorobogatov.domain.interactor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class SuspendUseCase<in P, R>() {

    suspend operator fun invoke(parameters: P): Result<R> {
        return try {
            withContext(Dispatchers.IO) {
                execute(parameters).let {
                    Result.success(it)
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}