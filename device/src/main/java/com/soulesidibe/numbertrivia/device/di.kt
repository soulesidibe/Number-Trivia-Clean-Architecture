package com.soulesidibe.numbertrivia.device

import android.content.Context
import android.util.Log
import com.soulesidibe.numbertrivia.data.local.datasource.NumberTriviaLocalDataSource
import com.soulesidibe.numbertrivia.data.network.NetworkInfo
import com.soulesidibe.numbertrivia.data.remote.datasource.NumberTriviaRemoteDataSource
import com.soulesidibe.numbertrivia.device.datasource.local.NumberTriviaLocalDataSourceImpl
import com.soulesidibe.numbertrivia.device.datasource.local.dataStore
import com.soulesidibe.numbertrivia.device.datasource.remote.NumberTriviaRemoteDataSourceImpl
import com.soulesidibe.numbertrivia.device.datasource.remote.api.NumberTriviaServices
import com.soulesidibe.numbertrivia.device.network.NetworkInfoImpl
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val deviceModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .baseUrl("http://numbersapi.com/")
            .build()
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request: Request = chain.request()
                val t1 = System.nanoTime()
                Log.i(
                    "OkHttp ==> ", String.format(
                        "Sending request %s on %s%n%s",
                        request.url, chain.connection(), request.headers
                    )
                )
                val response: Response = chain.proceed(request)

                val t2 = System.nanoTime()
                Log.i(
                    "OkHttp <== ", java.lang.String.format(
                        "Received response for %s in %.1fms%n%s",
                        response.request.url, (t2 - t1) / 1e6, response.headers
                    )
                )
                response
            }
            .build()
    }
    single<Moshi> { Moshi.Builder().build() }
    single<NumberTriviaServices> { get<Retrofit>().create(NumberTriviaServices::class.java) }

    single<NumberTriviaLocalDataSource> { NumberTriviaLocalDataSourceImpl(get()) }
    single<NumberTriviaRemoteDataSource> { NumberTriviaRemoteDataSourceImpl(get()) }
    single<NetworkInfo> { NetworkInfoImpl(androidContext()) }

    single { get<Context>().dataStore }
}