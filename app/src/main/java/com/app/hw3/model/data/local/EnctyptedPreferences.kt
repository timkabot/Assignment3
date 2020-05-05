package com.app.hw3.model.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.app.hw3.utils.Constants
import com.app.hw3.utils.Constants.SecureFileName
import com.app.hw3.utils.Constants.token

object EnctyptedPreferences {
    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    private val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

    lateinit var sharedPreferences: SharedPreferences

    fun createEncryptedPreferences(context: Context){
        sharedPreferences = EncryptedSharedPreferences
            .create(
                SecureFileName,
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
    }

    fun putToken(token: String){
        sharedPreferences.edit().putString(Constants.token, token).apply()
    }

    fun getToken(): String {
        return sharedPreferences.getString(token, "").toString()
    }
}