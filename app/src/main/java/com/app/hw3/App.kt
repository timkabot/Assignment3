package com.app.hw3

import android.app.Application
import com.app.hw3.di.DiModule
import com.app.hw3.model.data.local.EnctyptedPreferences
import toothpick.Toothpick
import toothpick.configuration.Configuration

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        createEncryptedPreferences()
        initToothpick()
        initAppScope()
    }

    private fun createEncryptedPreferences() {
        EnctyptedPreferences.createEncryptedPreferences(context = this)
    }


    private fun initToothpick() {
        Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
    }

    private fun initAppScope() {
        Toothpick.openScope("app_scope")
            .installModules(DiModule(applicationContext))
    }
}