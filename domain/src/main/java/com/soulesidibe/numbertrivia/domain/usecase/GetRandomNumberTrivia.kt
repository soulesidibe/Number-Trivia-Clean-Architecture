package com.soulesidibe.numbertrivia.domain.usecase

import com.soulesidibe.numbertrivia.domain.entity.NumberTriviaEntity
import com.soulesidibe.numbertrivia.domain.repository.NumberTriviaRepository

class GetRandomNumberTrivia(
    private val repository: NumberTriviaRepository
) : UseCase<None, NumberTriviaEntity> {
    override suspend fun execute(param: None): Result<NumberTriviaEntity> {
        return repository.getRandomNumberTrivia()
    }
}