package com.soulesidibe.numbertrivia.domain

import com.soulesidibe.numbertrivia.domain.usecase.GetNumberTrivia
import com.soulesidibe.numbertrivia.domain.usecase.GetRandomNumberTrivia
import org.koin.dsl.module

val domainModule = module {
    factory { GetNumberTrivia(get()) }
    factory { GetRandomNumberTrivia(get()) }
}