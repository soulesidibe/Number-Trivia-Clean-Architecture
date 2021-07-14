package com.soulesidibe.numbertrivia

import android.app.Application
import com.soulesidibe.numbertrivia.data.dataModule
import com.soulesidibe.numbertrivia.device.deviceModule
import com.soulesidibe.numbertrivia.domain.domainModule
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
            modules(deviceModule, dataModule, domainModule)
            androidContext(this@NumberTriviaApp)
        }

    }
}