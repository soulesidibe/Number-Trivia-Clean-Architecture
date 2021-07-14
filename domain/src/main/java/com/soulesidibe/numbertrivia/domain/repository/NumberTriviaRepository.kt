package com.soulesidibe.numbertrivia.domain.repository

import com.soulesidibe.numbertrivia.domain.entity.NumberTriviaEntity

interface NumberTriviaRepository {

    suspend fun getRandomNumberTrivia(): Result<NumberTriviaEntity>

    suspend fun getNumberTriviaFor(number: Long): Result<NumberTriviaEntity>
}