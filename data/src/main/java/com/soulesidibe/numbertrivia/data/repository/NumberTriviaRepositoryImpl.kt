package com.soulesidibe.numbertrivia.data.repository

import com.soulesidibe.numbertrivia.data.local.datasource.NumberTriviaLocalDataSource
import com.soulesidibe.numbertrivia.data.local.datasource.exception.CacheDataException
import com.soulesidibe.numbertrivia.data.local.datasource.exception.NoCacheFoundException
import com.soulesidibe.numbertrivia.data.model.NumberTriviaModel
import com.soulesidibe.numbertrivia.data.model.toEntity
import com.soulesidibe.numbertrivia.data.network.NetworkInfo
import com.soulesidibe.numbertrivia.data.remote.datasource.NumberTriviaRemoteDataSource
import com.soulesidibe.numbertrivia.data.remote.datasource.exception.NetworkException
import com.soulesidibe.numbertrivia.domain.entity.NumberTriviaEntity
import com.soulesidibe.numbertrivia.domain.repository.NumberTriviaRepository

internal class NumberTriviaRepositoryImpl(
    private val remoteDataSource: NumberTriviaRemoteDataSource,
    private val localDataSource: NumberTriviaLocalDataSource,
    private val networkInfo: NetworkInfo
) : NumberTriviaRepository {
    override suspend fun getRandomNumberTrivia(): Result<NumberTriviaEntity> {
        return getTrivia {
            remoteDataSource.getRandomNumberTrivia()
        }
    }

    override suspend fun getNumberTriviaFor(number: Long): Result<NumberTriviaEntity> {
        return getTrivia {
            remoteDataSource.getNumberTriviaFor(number)
        }
    }

    private suspend fun getTrivia(func: suspend () -> NumberTriviaModel): Result<NumberTriviaEntity> {
        return if (networkInfo.isConnected()) {
            try {
                val randomNumberTrivia = func()
                localDataSource.cacheNumberTrivia(randomNumberTrivia)
                Result.success(randomNumberTrivia.toEntity())
            } catch (e: NetworkException) {
                Result.failure(e)
            } catch (e: CacheDataException) {
                Result.failure(e)
            }
        } else {
            try {
                Result.success(localDataSource.getLastNumberTrivia().toEntity())
            } catch (e: NoCacheFoundException) {
                Result.failure(e)
            }
        }
    }
}