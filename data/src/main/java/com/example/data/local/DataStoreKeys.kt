package com.example.data.local

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    val EMAIL = stringPreferencesKey("email")
    val PROFILE_URL = stringPreferencesKey("profile_url")
    val NICKNAME = stringPreferencesKey("nickname")
    val DESCRIPTION = stringPreferencesKey("description")
    val PRIVATEID = stringPreferencesKey("private_id")
}