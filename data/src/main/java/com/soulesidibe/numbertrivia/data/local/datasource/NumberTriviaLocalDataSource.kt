package com.soulesidibe.numbertrivia.data.local.datasource

import com.soulesidibe.numbertrivia.data.local.datasource.exception.CacheDataException
import com.soulesidibe.numbertrivia.data.local.datasource.exception.NoCacheFoundException
import com.soulesidibe.numbertrivia.data.model.NumberTriviaModel
import kotlin.jvm.Throws

interface NumberTriviaLocalDataSource {
    @Throws(NoCacheFoundException::class)
    suspend fun getLastNumberTrivia(): NumberTriviaModel

    @Throws(CacheDataException::class)
    suspend fun cacheNumberTrivia(number: NumberTriviaModel)
}