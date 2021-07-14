package com.soulesidibe.numbertrivia.domain.entity

data class NumberTriviaEntity(
    val text: String,
    val number: Long,
    val found: Boolean,
    val type: String
)
