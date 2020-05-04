package com.app.assignment3

import android.app.Application
import com.app.assignment3.di.createAppModule
import com.app.assignment3.model.data.local.Prefs
import com.github.ajalt.timberkt.Timber
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber.DebugTree




class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initSecurePrefs()
        initKoin()
        initTimber()
    }

    private fun initTimber(){
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    private fun initSecurePrefs() {
        Prefs.createEncryptedPreferences(context = this)
    }
    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    createAppModule()
                )
            )
        }
    }
}