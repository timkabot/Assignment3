package com.app.hw3.presentation.petList

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.hw3.domain.useCases.PetUseCase
import com.app.hw3.domain.entity.Pet
import com.app.hw3.domain.entity.AnimalType
import com.app.hw3.domain.entity.Breed
import com.app.hw3.model.data.local.EnctyptedPreferences
import com.app.hw3.model.data.server.PetFinderApi
import com.app.hw3.model.data.server.TokenResponse
import com.app.hw3.utils.Constants
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PetListViewModel @Inject constructor(private val petInteractor: PetUseCase, private val api: PetFinderApi, private val context: Context) : ViewModel() {
    private val disposables = CompositeDisposable()

    val animalTypes = MutableLiveData<List<AnimalType>>()
    val breeds = MutableLiveData<List<Breed>>()
    val animals = MutableLiveData<List<Pet>>()
    val progressStatus = MutableLiveData<String>()
    lateinit var lastDetailedAnimal : Pet
    fun getAnimalTypes() {
        petInteractor.getAnimalTypes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                animalTypes.value = it
                progressStatus.value = "hide"
            }
            .addTo(disposables)
    }

    fun getBreeds(name: String) {
        progressStatus.value = "show"

        petInteractor.getBreeds(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                breeds.value = it
                progressStatus.value = "hide"
            }
            .addTo(disposables)
    }

    fun getAnimals(type: String, breed: String) {
        progressStatus.value = "show"
        petInteractor.getAnimals(type, breed)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                animals.value = it
                progressStatus.value = "hide"
            }
            .addTo(disposables)
    }

    fun getAnimals() {
        progressStatus.value = "show"

        petInteractor.getAnimals()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                animals.value = it
                progressStatus.value = "hide"

            }
            .addTo(disposables)
    }

    fun initToken() {
        progressStatus.postValue("show")
        api.getAuthToken("client_credentials", Constants.ClientId, Constants.SecretKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<TokenResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onError(e: Throwable) {
                    getAnimalTypes()
                }

                override fun onSuccess(value: TokenResponse) {
                    value.run {
                        EnctyptedPreferences.putToken(accessToken)
                        getAnimalTypes()
                    }
                }

            })
    }

    fun dispose() {
        disposables.dispose()
    }
}