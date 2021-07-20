package com.soulesidibe.numbertrivia.device.datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.soulesidibe.numbertrivia.data.local.datasource.NumberTriviaLocalDataSource
import com.soulesidibe.numbertrivia.data.local.datasource.exception.NoCacheFoundException
import com.soulesidibe.numbertrivia.data.model.NumberTriviaModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*


internal class NumberTriviaLocalDataSourceImpl(
    private val dataStore: DataStore<Preferences>
) : NumberTriviaLocalDataSource {

    private val triviaNumber by lazy { longPreferencesKey("trivia_number") }
    private val triviaText by lazy { stringPreferencesKey("trivia_text") }
    private val triviaType by lazy { stringPreferencesKey("trivia_type") }
    private val triviaFound by lazy { booleanPreferencesKey("trivia_found") }

    @ExperimentalCoroutinesApi
    override suspend fun getLastNumberTrivia(): NumberTriviaModel {
        return dataStore.data.transform { preferences ->
            check(preferences.contains(triviaText))

            val numberTriviaModel = NumberTriviaModel(
                preferences[triviaText] ?: "",
                preferences[triviaNumber] ?: 0,
                preferences[triviaFound] ?: false,
                preferences[triviaType] ?: ""
            )
            emit(numberTriviaModel)
        }.catch {
            throw NoCacheFoundException()
        }.onCompletion { cause ->

        }.first()
    }

    override suspend fun cacheNumberTrivia(number: NumberTriviaModel) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[triviaFound] = number.found
            mutablePreferences[triviaText] = number.text
            mutablePreferences[triviaNumber] = number.number
            mutablePreferences[triviaType] = number.type
        }
    }
}