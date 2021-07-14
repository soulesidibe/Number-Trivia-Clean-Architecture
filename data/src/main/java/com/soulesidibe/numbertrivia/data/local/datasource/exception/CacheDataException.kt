package com.soulesidibe.numbertrivia.data.local.datasource.exception

import com.soulesidibe.numbertrivia.data.model.NumberTriviaModel

data class CacheDataException(val data: NumberTriviaModel): Exception()