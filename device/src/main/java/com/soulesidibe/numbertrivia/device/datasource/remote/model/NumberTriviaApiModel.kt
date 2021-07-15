package com.soulesidibe.numbertrivia.device.datasource.remote.model

import com.soulesidibe.numbertrivia.data.model.NumberTriviaModel

internal data class NumberTriviaApiModel(
    val text: String,
    val number: Long,
    val found: Boolean,
    val type: String
)

internal fun NumberTriviaApiModel.toModel(): NumberTriviaModel {
    return NumberTriviaModel(text, number, found, type)
}