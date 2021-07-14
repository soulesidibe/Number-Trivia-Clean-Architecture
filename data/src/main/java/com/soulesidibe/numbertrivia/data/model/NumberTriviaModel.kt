package com.soulesidibe.numbertrivia.data.model

import com.soulesidibe.numbertrivia.domain.entity.NumberTriviaEntity

data class NumberTriviaModel(
    val text: String,
    val number: Long,
    val found: Boolean,
    val type: String
)

internal fun NumberTriviaModel.toEntity() = NumberTriviaEntity(text, number, found, type)