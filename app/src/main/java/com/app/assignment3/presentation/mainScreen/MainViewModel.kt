package com.app.assignment3.presentation.mainScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.assignment3.domain.PetInteractor
import com.app.assignment3.domain.entity.Animal
import com.app.assignment3.domain.entity.AnimalType
import com.app.assignment3.domain.entity.Breed
import com.app.assignment3.model.data.local.Prefs
import com.app.assignment3.model.data.server.ApiService
import com.app.assignment3.model.data.server.response.TokenResponse
import com.app.assignment3.utils.Constants
import com.github.ajalt.timberkt.Timber
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val petInteractor: PetInteractor, private val api: ApiService) : ViewModel() {
    private val disposables = CompositeDisposable()

    val animalTypes = MutableLiveData<List<AnimalType>>()
    val breeds = MutableLiveData<List<Breed>>()
    val animals = MutableLiveData<List<Animal>>()
    val progressStatus = MutableLiveData<String>()
    lateinit var lastDetailedAnimal : Animal
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
        petInteractor.getBreeds(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                breeds.value = it
            }
            .addTo(disposables)
    }

    fun getAnimals(type: String, breed: String) {
        petInteractor.getAnimals(type, breed)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                animals.value = it
            }
            .addTo(disposables)
    }

    fun getAnimals() {
        petInteractor.getAnimals()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                animals.value = it
            }
            .addTo(disposables)
    }

    fun initToken() {
        api.getAuthToken("client_credentials", Constants.ClientId, Constants.SecretKey)
            .subscribeOn(Schedulers.io())
            .subscribe(object : SingleObserver<TokenResponse> {
                override fun onSubscribe(d: Disposable) {}
                override fun onError(e: Throwable) {
                    Timber.d { "TokenRequest:  Could not get token : $e" }
                    getAnimalTypes()
                }

                override fun onSuccess(value: TokenResponse) {
                    value.run {
                        Timber.d { "TokenRequest: Token was succesfully taken" }
                        Prefs.putToken(accessToken)
                        progressStatus.postValue("show")
                        getAnimalTypes()
                    }
                }

            })
    }

    fun dispose() {
        disposables.dispose()
    }
}