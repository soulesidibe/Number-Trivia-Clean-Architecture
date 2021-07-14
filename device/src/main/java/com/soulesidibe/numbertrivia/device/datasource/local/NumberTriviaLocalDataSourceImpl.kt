package com.soulesidibe.numbertrivia.device.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.soulesidibe.numbertrivia.data.local.datasource.NumberTriviaLocalDataSource
import com.soulesidibe.numbertrivia.data.local.datasource.exception.NoCacheFoundException
import com.soulesidibe.numbertrivia.data.model.NumberTriviaModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single

internal val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


internal class NumberTriviaLocalDataSourceImpl(
    private val dataStore: DataStore<Preferences>
) : NumberTriviaLocalDataSource {

    private val triviaNumber by lazy { longPreferencesKey("trivia_number") }
    private val triviaText by lazy { stringPreferencesKey("trivia_text") }
    private val triviaType by lazy { stringPreferencesKey("trivia_type") }
    private val triviaFound by lazy { booleanPreferencesKey("trivia_found") }

    override suspend fun getLastNumberTrivia(): NumberTriviaModel {
        return dataStore.data.map { preferences ->
            val number = preferences[triviaNumber]!!
            val text = preferences[triviaText]!!
            val type = preferences[triviaType]!!
            val found = preferences[triviaFound]!!
            NumberTriviaModel(text, number, found, type)
        }.catch {
            throw NoCacheFoundException()
        }.single()
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