package com.soulesidibe.numbertrivia.model

import com.soulesidibe.numbertrivia.domain.entity.NumberTriviaEntity

data class NumberTriviaUiModel(val number: Long, val description: String)

internal fun NumberTriviaEntity.toUiModel() = NumberTriviaUiModel(this.number, this.text)

