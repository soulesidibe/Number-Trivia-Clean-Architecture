package com.soulesidibe.numbertrivia.device.datasource.remote

import com.soulesidibe.numbertrivia.data.model.NumberTriviaModel
import com.soulesidibe.numbertrivia.data.remote.datasource.NumberTriviaRemoteDataSource
import com.soulesidibe.numbertrivia.data.remote.datasource.exception.NetworkException
import com.soulesidibe.numbertrivia.device.datasource.remote.api.NumberTriviaServices
import com.soulesidibe.numbertrivia.device.datasource.remote.model.toModel


internal class NumberTriviaRemoteDataSourceImpl(
    private val service: NumberTriviaServices
) : NumberTriviaRemoteDataSource {

    override suspend fun getRandomNumberTrivia(): NumberTriviaModel {
        try {
            val response = service.getRandomNumberTrivia()
            if (response.isSuccessful) {
                return response.body()?.toModel() ?: throw NetworkException()
            } else {
                throw NetworkException()
            }
        } catch (e: Exception) {
            throw NetworkException()
        }

    }

    override suspend fun getNumberTriviaFor(number: Long): NumberTriviaModel {
        try {
            val response = service.getConcreteNumberTrivia(number)
            if (response.isSuccessful) {
                return response.body()?.toModel() ?: throw NetworkException()
            } else {
                throw NetworkException()
            }
        } catch (e: Exception) {
            throw NetworkException()
        }
    }
}