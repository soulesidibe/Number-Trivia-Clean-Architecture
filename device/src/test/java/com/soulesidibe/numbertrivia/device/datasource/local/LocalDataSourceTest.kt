package com.soulesidibe.numbertrivia.device.datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.soulesidibe.numbertrivia.data.local.datasource.exception.NoCacheFoundException
import com.soulesidibe.numbertrivia.data.model.NumberTriviaModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class LocalDataSourceTest {

    @Test(expected = NoCacheFoundException::class)
    fun `when cache is empty should throw an exception`() = runBlockingTest {
        val dataStore = Mockito.mock(DataStore::class.java) as DataStore<Preferences>
        val localDataSource = NumberTriviaLocalDataSourceImpl(dataStore)

        `when`(dataStore.data).thenReturn(flowOf(emptyPreferences()))

        localDataSource.getLastNumberTrivia()
    }


    @Test
    fun `when cache contains data should return it`() = runBlockingTest {
        val dataStore = Mockito.mock(DataStore::class.java) as DataStore<Preferences>
        val localDataSource = NumberTriviaLocalDataSourceImpl(dataStore)

        `when`(dataStore.data).thenReturn(
            flowOf(
                preferencesOf(
                    longPreferencesKey("trivia_number") to 100L,
                    stringPreferencesKey("trivia_text") to "Test test",
                    stringPreferencesKey("trivia_type") to "trivia",
                    booleanPreferencesKey("trivia_found") to true
                )
            )
        )

        val lastNumberTrivia = localDataSource.getLastNumberTrivia()
        Assert.assertEquals(NumberTriviaModel("Test test", 100L, true, "trivia"), lastNumberTrivia)
    }

    @Test
    fun `should cache the given number trivia`() = runBlockingTest {
        val dataStore = Mockito.mock(DataStore::class.java) as DataStore<Preferences>
        val localDataSource = NumberTriviaLocalDataSourceImpl(dataStore)

        localDataSource.cacheNumberTrivia(NumberTriviaModel("Test test", 100L, true, "trivia"))
        verify(dataStore).edit(any())
    }

}