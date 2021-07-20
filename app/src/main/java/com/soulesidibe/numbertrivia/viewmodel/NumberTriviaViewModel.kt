package com.soulesidibe.numbertrivia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soulesidibe.numbertrivia.domain.usecase.GetNumberTrivia
import com.soulesidibe.numbertrivia.domain.usecase.GetRandomNumberTrivia
import com.soulesidibe.numbertrivia.domain.usecase.None
import com.soulesidibe.numbertrivia.model.NumberTriviaUiModel
import com.soulesidibe.numbertrivia.model.toUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class NumberTriviaViewModel(
    private val getRandomNumberTrivia: GetRandomNumberTrivia,
    private val getNumberTrivia: GetNumberTrivia
) : ViewModel() {

    private val _numberTriviaLiveData: MutableLiveData<Result<NumberTriviaUiModel>> =
        MutableLiveData()
    internal val numberTriviaLiveData: LiveData<Result<NumberTriviaUiModel>> = _numberTriviaLiveData

    fun getRandomNumberTrivia() {
        viewModelScope.launch {
            _numberTriviaLiveData.postValue(
                getRandomNumberTrivia.execute(None).map { it.toUiModel() }
            )
        }
    }

    fun getConcreteNumberTrivia(number: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            val execute = getNumberTrivia.execute(number)
            if (execute.isSuccess) {
                _numberTriviaLiveData.postValue(execute.map { it.toUiModel() })
            } else {
                _numberTriviaLiveData.postValue(Result.failure(execute.exceptionOrNull()!!))
            }
        }
    }
}