package com.soulesidibe.numbertrivia.device.datasource.remote.api

import com.soulesidibe.numbertrivia.device.datasource.remote.model.NumberTriviaApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


internal interface NumberTriviaServices {

    @GET("random/trivia?json")
    suspend fun getRandomNumberTrivia(): Response<NumberTriviaApiModel>

    @GET("{number}/trivia?json")
    suspend fun getConcreteNumberTrivia(@Path("number") number: Long): Response<NumberTriviaApiModel>
}