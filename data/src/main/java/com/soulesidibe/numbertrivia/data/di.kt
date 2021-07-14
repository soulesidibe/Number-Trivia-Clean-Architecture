package com.soulesidibe.numbertrivia.data

import com.soulesidibe.numbertrivia.data.repository.NumberTriviaRepositoryImpl
import com.soulesidibe.numbertrivia.domain.repository.NumberTriviaRepository
import org.koin.dsl.module

val dataModule = module {
    single<NumberTriviaRepository> { NumberTriviaRepositoryImpl(get(), get(), get()) }
}