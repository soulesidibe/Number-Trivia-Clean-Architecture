package com.soulesidibe.numbertrivia.domain.usecase

internal interface UseCase<in Input, out Output> {

    suspend fun execute(param: Input): Result<Output>
}