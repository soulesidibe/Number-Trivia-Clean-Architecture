package com.soulesidibe.numbertrivia

import android.app.Application
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NumberTriviaApp : Application() {

    @DelicateCoroutinesApi
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@NumberTriviaApp)
        }

    }
}