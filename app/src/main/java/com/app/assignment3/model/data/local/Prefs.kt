package com.app.assignment3.model.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.app.assignment3.utils.Constants.SecureFileName
import com.app.assignment3.utils.Constants.TokenKey

object Prefs {
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
        sharedPreferences.edit().putString(TokenKey, token).apply()
    }

    fun getToken(): String {
        return sharedPreferences.getString(TokenKey, "").toString()
    }
}