package com.soulesidibe.numbertrivia.data.remote.datasource

import com.soulesidibe.numbertrivia.data.model.NumberTriviaModel
import com.soulesidibe.numbertrivia.data.remote.datasource.exception.NetworkException
import kotlin.jvm.Throws

interface NumberTriviaRemoteDataSource {
    @Throws(NetworkException::class)
    suspend fun getRandomNumberTrivia(): NumberTriviaModel

    @Throws(NetworkException::class)
    suspend fun getNumberTriviaFor(number: Long): NumberTriviaModel
}