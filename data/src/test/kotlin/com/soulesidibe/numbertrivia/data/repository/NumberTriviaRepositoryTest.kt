package com.soulesidibe.numbertrivia.data.repository

import com.soulesidibe.numbertrivia.data.local.datasource.NumberTriviaLocalDataSource
import com.soulesidibe.numbertrivia.data.local.datasource.exception.CacheDataException
import com.soulesidibe.numbertrivia.data.local.datasource.exception.NoCacheFoundException
import com.soulesidibe.numbertrivia.data.model.NumberTriviaModel
import com.soulesidibe.numbertrivia.data.network.NetworkInfo
import com.soulesidibe.numbertrivia.data.remote.datasource.NumberTriviaRemoteDataSource
import com.soulesidibe.numbertrivia.data.remote.datasource.exception.NetworkException
import com.soulesidibe.numbertrivia.domain.entity.NumberTriviaEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class NumberTriviaRepositoryTest {

    @Test
    fun `when internet is off and no local data found should return a failure`() = runBlockingTest {
        val remoteDataSource = Mockito.mock(NumberTriviaRemoteDataSource::class.java)
        val localDataSource = Mockito.mock(NumberTriviaLocalDataSource::class.java)
        val networkInfo = Mockito.mock(NetworkInfo::class.java)

        Mockito.`when`(networkInfo.isConnected()).thenAnswer {
            false
        }
        Mockito.`when`(localDataSource.getLastNumberTrivia()).thenThrow(NoCacheFoundException())

        val repository = NumberTriviaRepositoryImpl(
            remoteDataSource,
            localDataSource,
            networkInfo
        )
        val randomNumberTrivia = repository.getRandomNumberTrivia()

        verify(networkInfo).isConnected()
        verify(localDataSource).getLastNumberTrivia()
        Assert.assertTrue(randomNumberTrivia.isFailure)
        Assert.assertTrue(randomNumberTrivia.exceptionOrNull() is NoCacheFoundException)
    }

    @Test
    fun `when internet is off and no local data found should return a failure 1`() = runBlockingTest {
        val remoteDataSource = Mockito.mock(NumberTriviaRemoteDataSource::class.java)
        val localDataSource = Mockito.mock(NumberTriviaLocalDataSource::class.java)
        val networkInfo = Mockito.mock(NetworkInfo::class.java)

        Mockito.`when`(networkInfo.isConnected()).thenAnswer {
            false
        }
        Mockito.`when`(localDataSource.getLastNumberTrivia()).thenThrow(NoCacheFoundException())

        val repository = NumberTriviaRepositoryImpl(
            remoteDataSource,
            localDataSource,
            networkInfo
        )
        val randomNumberTrivia = repository.getNumberTriviaFor(100)

        verify(networkInfo).isConnected()
        verify(localDataSource).getLastNumberTrivia()
        Assert.assertTrue(randomNumberTrivia.isFailure)
        Assert.assertTrue(randomNumberTrivia.exceptionOrNull() is NoCacheFoundException)
    }

    @Test
    fun `when internet is off and local data found should return the last number`() = runBlockingTest {
        val remoteDataSource = Mockito.mock(NumberTriviaRemoteDataSource::class.java)
        val localDataSource = Mockito.mock(NumberTriviaLocalDataSource::class.java)
        val networkInfo = Mockito.mock(NetworkInfo::class.java)

        Mockito.`when`(networkInfo.isConnected()).thenAnswer {
            false
        }
        Mockito.`when`(localDataSource.getLastNumberTrivia()).thenAnswer {
            Result.success(NumberTriviaModel("Test test", 100, true, "trivia"))
        }

        val repository = NumberTriviaRepositoryImpl(
            remoteDataSource,
            localDataSource,
            networkInfo
        )
        val randomNumberTrivia = repository.getRandomNumberTrivia()

        verify(networkInfo).isConnected()
        verify(localDataSource).getLastNumberTrivia()
        Assert.assertTrue(randomNumberTrivia.isSuccess)
        val value = randomNumberTrivia.getOrNull()
        Assert.assertTrue(value is NumberTriviaEntity)
        Assert.assertTrue(value?.run {
            text == "Test test" && number == 100L && found && type == "trivia"
        } == true)
    }

    @Test
    fun `when internet in on should return the random trivia`() = runBlockingTest {
        val remoteDataSource = Mockito.mock(NumberTriviaRemoteDataSource::class.java)
        val localDataSource = Mockito.mock(NumberTriviaLocalDataSource::class.java)
        val networkInfo = Mockito.mock(NetworkInfo::class.java)

        Mockito.`when`(networkInfo.isConnected()).thenAnswer {
            true
        }
        Mockito.`when`(remoteDataSource.getRandomNumberTrivia()).thenAnswer {
            Result.success(NumberTriviaModel("Test test", 100, true, "trivia"))
        }

        val repository = NumberTriviaRepositoryImpl(
            remoteDataSource,
            localDataSource,
            networkInfo
        )
        val randomNumberTrivia = repository.getRandomNumberTrivia()

        verify(networkInfo).isConnected()
        verify(remoteDataSource).getRandomNumberTrivia()
        verify(localDataSource).cacheNumberTrivia(NumberTriviaModel("Test test", 100, true, "trivia"))
        Assert.assertTrue(randomNumberTrivia.isSuccess)
        val value = randomNumberTrivia.getOrNull()
        Assert.assertTrue(value is NumberTriviaEntity)
        Assert.assertTrue(value?.run {
            text == "Test test" && number == 100L && found && type == "trivia"
        } == true)
    }

    @Test
    fun `when internet in on and a server error occurs should return a failure`() = runBlockingTest {
        val remoteDataSource = Mockito.mock(NumberTriviaRemoteDataSource::class.java)
        val localDataSource = Mockito.mock(NumberTriviaLocalDataSource::class.java)
        val networkInfo = Mockito.mock(NetworkInfo::class.java)

        Mockito.`when`(networkInfo.isConnected()).thenAnswer {
            true
        }
        Mockito.`when`(remoteDataSource.getRandomNumberTrivia()).thenThrow(NetworkException())
        val repository = NumberTriviaRepositoryImpl(
            remoteDataSource,
            localDataSource,
            networkInfo
        )
        val randomNumberTrivia = repository.getRandomNumberTrivia()

        verify(networkInfo).isConnected()
        verify(remoteDataSource).getRandomNumberTrivia()

        Assert.assertTrue(randomNumberTrivia.isFailure)
        val value = randomNumberTrivia.exceptionOrNull()
        Assert.assertTrue(value is NetworkException)
    }

    @Test
    fun `when internet in on, server return the number but cache failed  should return the value`() = runBlockingTest {
        val remoteDataSource = Mockito.mock(NumberTriviaRemoteDataSource::class.java)
        val localDataSource = Mockito.mock(NumberTriviaLocalDataSource::class.java)
        val networkInfo = Mockito.mock(NetworkInfo::class.java)

        Mockito.`when`(networkInfo.isConnected()).thenAnswer {
            true
        }
        Mockito.`when`(remoteDataSource.getRandomNumberTrivia()).thenAnswer {
            Result.success(NumberTriviaModel("Test test", 100, true, "trivia"))
        }
        Mockito.`when`(localDataSource.cacheNumberTrivia(any())).thenThrow(
            CacheDataException(
                NumberTriviaModel("Test test", 100, true, "trivia")
            )
        )

        val repository = NumberTriviaRepositoryImpl(
            remoteDataSource,
            localDataSource,
            networkInfo
        )
        val randomNumberTrivia = repository.getRandomNumberTrivia()

        verify(networkInfo).isConnected()
        verify(remoteDataSource).getRandomNumberTrivia()
        verify(localDataSource).cacheNumberTrivia(NumberTriviaModel("Test test", 100, true, "trivia"))

        Assert.assertTrue(randomNumberTrivia.isSuccess)
        val value = randomNumberTrivia.getOrNull()
        Assert.assertTrue(value is NumberTriviaEntity)
        Assert.assertTrue(value?.run {
            text == "Test test" && number == 100L && found && type == "trivia"
        } == true)
    }

}