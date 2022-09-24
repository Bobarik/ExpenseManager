package com.gmail.vlaskorobogatov.domain.interactor

abstract class UseCase<in P, R> {

    operator fun invoke(parameters: P): Result<R> {
        return try {
            execute(parameters).let {
                Result.success(it)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract fun execute(parameters: P): R
}