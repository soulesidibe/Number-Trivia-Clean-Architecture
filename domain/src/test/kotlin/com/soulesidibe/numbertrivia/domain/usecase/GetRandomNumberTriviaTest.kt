package com.soulesidibe.numbertrivia.domain.usecase

import com.soulesidibe.numbertrivia.domain.domainModule
import com.soulesidibe.numbertrivia.domain.entity.NumberTriviaEntity
import com.soulesidibe.numbertrivia.domain.repository.NumberTriviaRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.koin.test.check.checkModules
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.verify

class GetRandomNumberTriviaTest {
    @Test
    fun `when no internet and cache empty should return a failure`() = runBlocking {
        val repository = Mockito.mock(NumberTriviaRepository::class.java)

        Mockito.`when`(repository.getRandomNumberTrivia()).thenAnswer {
            Result.failure<Exception>(Exception())
        }

        val useCase = GetRandomNumberTrivia(repository)
        val result = useCase.execute(None)
        verify(repository).getRandomNumberTrivia()
        Assert.assertTrue(result.isFailure)
        Assert.assertTrue(result.getOrNull() == null)
        Assert.assertTrue(result.exceptionOrNull() is Exception)
    }

    @Test
    fun `when internet ok should return  the number trivia`() = runBlocking {
        val repository = Mockito.mock(NumberTriviaRepository::class.java)

        Mockito.`when`(repository.getRandomNumberTrivia()).thenAnswer {
            Result.success(NumberTriviaEntity("Test test", 100, true, "trivia"))
        }

        val useCase = GetRandomNumberTrivia(repository)
        val result = useCase.execute(None)
        verify(repository).getRandomNumberTrivia()
        Assert.assertTrue(result.isSuccess)
        val numberTriviaEntity: NumberTriviaEntity? = result.getOrNull()
        Assert.assertTrue(numberTriviaEntity is NumberTriviaEntity)
        Assert.assertTrue(numberTriviaEntity?.run {
            text == "Test test" && number == 100L && found && type == "trivia"
        } == true)
        Assert.assertTrue(result.exceptionOrNull() == null)
    }

}