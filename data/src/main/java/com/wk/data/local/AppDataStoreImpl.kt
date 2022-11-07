package com.wk.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.wk.data.remote.responses.LotteryInfoResponse
import com.wk.domain.models.ui.LotteryInfoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : AppDataStore {

    override suspend fun putValue(key: Preferences.Key<String>, value: String) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    override suspend fun putValue(key: Preferences.Key<Boolean>, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    override fun observeString(key: Preferences.Key<String>): Flow<String?> =
        dataStore.data.map { it[key] }

    override fun observeBoolean(key: Preferences.Key<Boolean>): Flow<Boolean> =
        dataStore.data.map { it[key] ?: true }

    override suspend fun setLotteryInfo(response: LotteryInfoModel?) {
        if (response != null) {
//            putValue(DataStoreKeys.EMAIL, response.email)
//            putValue(DataStoreKeys.DESCRIPTION, response.description.toString())
//            putValue(DataStoreKeys.NICKNAME, response.nickname)
//            putValue(DataStoreKeys.PROFILE_URL, response.profile_url.toString())
//            putValue(DataStoreKeys.PRIVATEID, response.private_id)
        }
    }
}