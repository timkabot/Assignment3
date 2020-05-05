package com.app.hw3.di

import android.content.Context
import com.app.hw3.model.data.local.ApplicationDatabase
import com.app.hw3.model.data.server.PetFinderApi
import com.app.hw3.utils.Constants.BaseURL
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module

class DiModule(context: Context) : Module() {
    init {
        bind(Context::class.java).toInstance(context)

        // Navigation
        val cicerone = Cicerone.create()
        bind(Router::class.java).toInstance(cicerone.router)
        bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)
        bind(PetFinderApi::class.java).toInstance(
            PetFinderApi.create(baseUrl = BaseURL)
        )
        bind(ApplicationDatabase::class.java).toInstance(ApplicationDatabase.build(context))
    }
}
