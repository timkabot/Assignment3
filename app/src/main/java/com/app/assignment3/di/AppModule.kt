package com.app.assignment3.di

import com.app.assignment3.presentation.mainScreen.MainViewModel
import com.app.assignment3.domain.PetInteractor
import com.app.assignment3.model.data.AnimalRepository
import com.app.assignment3.model.data.local.AppDatabase
import com.app.assignment3.model.data.server.ApiService
import com.app.assignment3.utils.Constants.BaseURL
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun createAppModule(baseUrl: String = BaseURL) = module {
    single { ApiService.create(baseUrl) }
    single { PetInteractor(get())}
    single { MainViewModel(get(), get()) }
    single { AnimalRepository(get(), get(), get()) }
    single { AppDatabase.build(get()) }

}