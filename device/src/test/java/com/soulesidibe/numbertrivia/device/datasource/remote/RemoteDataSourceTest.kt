package com.soulesidibe.numbertrivia.device.datasource.remote

import com.soulesidibe.numbertrivia.data.model.NumberTriviaModel
import com.soulesidibe.numbertrivia.data.remote.datasource.exception.NetworkException
import com.soulesidibe.numbertrivia.device.datasource.remote.api.NumberTriviaServices
import com.soulesidibe.numbertrivia.device.datasource.remote.model.NumberTriviaApiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteDataSourceTest {

    @Test
    fun `sould return the random number trivia when server is ok`() = runBlockingTest {
        val service = mock<NumberTriviaServices>()

        `when`(service.getRandomNumberTrivia()).thenAnswer {
            Response.success(
                200,
                NumberTriviaApiModel("Test test", 100, true, "trivia"),
            )
        }

        val remote = NumberTriviaRemoteDataSourceImpl(service)
        val randomNumberTrivia = remote.getRandomNumberTrivia()
        Assert.assertTrue(randomNumberTrivia == NumberTriviaModel("Test test", 100, true, "trivia"))
    }

    @Test(expected = NetworkException::class)
    fun `sould throw an exception when server is ko`() = runBlockingTest {
        val service = mock<NumberTriviaServices>()

        `when`(service.getRandomNumberTrivia()).thenAnswer {
            Response.error<Unit>(
                400, "{}".toResponseBody()
            )
        }

        val remote = NumberTriviaRemoteDataSourceImpl(service)
        remote.getRandomNumberTrivia()
    }

    @Test(expected = NetworkException::class)
    fun `sould throw an exception when server is ok but data corrupted`() = runBlockingTest {
        val service = mock<NumberTriviaServices>()

        `when`(service.getRandomNumberTrivia()).thenAnswer {
            Response.error<NumberTriviaApiModel>(
                200, null
            )
        }

        val remote = NumberTriviaRemoteDataSourceImpl(service)
        remote.getRandomNumberTrivia()
    }



    @Test
    fun `sould return the concrete number trivia when server is ok`() = runBlockingTest {
        val service = mock<NumberTriviaServices>()

        `when`(service.getConcreteNumberTrivia(100)).thenAnswer {
            Response.success(
                200,
                NumberTriviaApiModel("Test test", 100, true, "trivia"),
            )
        }

        val remote = NumberTriviaRemoteDataSourceImpl(service)
        val randomNumberTrivia = remote.getNumberTriviaFor(100)
        Assert.assertTrue(randomNumberTrivia == NumberTriviaModel("Test test", 100, true, "trivia"))
    }

    @Test(expected = NetworkException::class)
    fun `sould throw an exception when server is ko 1`() = runBlockingTest {
        val service = mock<NumberTriviaServices>()

        `when`(service.getConcreteNumberTrivia(100)).thenAnswer {
            Response.error<Unit>(
                400, "{}".toResponseBody()
            )
        }

        val remote = NumberTriviaRemoteDataSourceImpl(service)
        remote.getNumberTriviaFor(100)
    }

    @Test(expected = NetworkException::class)
    fun `sould throw an exception when server is ok but data corrupted 1`() = runBlockingTest {
        val service = mock<NumberTriviaServices>()

        `when`(service.getConcreteNumberTrivia(100)).thenAnswer {
            Response.error<NumberTriviaApiModel>(
                200, null
            )
        }

        val remote = NumberTriviaRemoteDataSourceImpl(service)
        remote.getNumberTriviaFor(100)
    }

}