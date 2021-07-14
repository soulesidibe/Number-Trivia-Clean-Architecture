package com.soulesidibe.numbertrivia.domain.usecase

import com.soulesidibe.numbertrivia.domain.entity.NumberTriviaEntity
import com.soulesidibe.numbertrivia.domain.repository.NumberTriviaRepository

class GetNumberTrivia(
    private val repository: NumberTriviaRepository
) : UseCase<Long, NumberTriviaEntity>{
    override suspend fun execute(param: Long): Result<NumberTriviaEntity> {
        return repository.getNumberTriviaFor(param)
    }
}