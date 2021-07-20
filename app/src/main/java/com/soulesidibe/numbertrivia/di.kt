package com.soulesidibe.numbertrivia

import com.soulesidibe.numbertrivia.viewmodel.NumberTriviaViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {
    viewModel { NumberTriviaViewModel(get(), get()) }
}