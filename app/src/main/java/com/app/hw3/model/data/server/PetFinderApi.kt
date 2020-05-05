package com.app.hw3.model.data.server

import com.app.hw3.model.data.local.EnctyptedPreferences
import com.app.hw3.utils.Constants
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface PetFinderApi {
    @FormUrlEncoded
    @POST("v2/oauth2/token")
    fun getAuthToken(
        @Field("grant_type") clientCredentials: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): Single<TokenResponse>

    @GET("v2/types")
    fun getAnimalTypes(): Single<PetTypeNetworkResponse>

    @GET("v2/types/{type}/breeds")
    fun getBreeds(@Path("type") animalTypeName: String): Single<BreedsNetworkResponse>

    @GET("v2/animals")
    fun getAnimals(@Query("type") type: String, @Query("breed") breed: String) : Single<PetListNetworkResponse>

    @GET("v2/animals")
    fun getAnimals() : Observable<PetListNetworkResponse>

    companion object Factory {
        private fun createNetworkClient(isDebug: Boolean = true): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level =
                    if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }
            return OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                .addNetworkInterceptor(createAccessTokenProvidingInterceptor())
                .build()
        }

        fun create(baseUrl: String = Constants.BaseURL): PetFinderApi {
            val gson = GsonBuilder() // Add null defense Adapter
                .enableComplexMapKeySerialization()
                .setPrettyPrinting()
                .setLenient()
                .serializeNulls()
                .create()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .client(createNetworkClient())
                .build()

            return retrofit.create(PetFinderApi::class.java)
        }

        private val accessToken: String
            get() = EnctyptedPreferences.getToken()

        private fun createAccessTokenProvidingInterceptor() = Interceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $accessToken")
                    .build()
            )
        }
    }
}