package com.example.comprepoupe.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.comprepoupe.presentation.fragments.FragmentScreenOfLogin
import kotlinx.coroutines.flow.first

class UserMenager(val context: Context) {


    companion object {
        val Context.dataUser: DataStore<Preferences> by preferencesDataStore(name = "user")
        private val USER_EMAIL_KEY = stringPreferencesKey("USER_NAME")
        private val USER_SENHA_KEY = stringPreferencesKey("USER_SENHA")
        private val USER_CHECK_KEY = booleanPreferencesKey("USER_CHECK")
    }

    suspend fun saveDataUser(email: String, senha: String, checkBox: Boolean) {
        context.dataUser.edit {
            it[USER_EMAIL_KEY] = email
            it[USER_SENHA_KEY] = senha
            it[USER_CHECK_KEY] = checkBox
        }
    }


    suspend fun readDataUser(): User {
        val prefs = context.dataUser.data.first()

        return User(
            email = prefs[USER_EMAIL_KEY] ?: "",
            senha = prefs[USER_SENHA_KEY] ?: "",
            checkBox = prefs[USER_CHECK_KEY] ?: false
        )
    }


    suspend fun clearDataUser(){
        context.dataUser.edit { it.clear() }
    }


}

