package com.example.data.local

import androidx.datastore.preferences.core.Preferences
import com.example.data.remote.responses.LotteryInfoResponse
import com.example.domain.models.ui.LotteryInfoModel
import kotlinx.coroutines.flow.Flow

interface AppDataStore {

    suspend fun putValue(key: Preferences.Key<String>, value: String)

    suspend fun putValue(key: Preferences.Key<Boolean>, value: Boolean)

    fun observeString(key: Preferences.Key<String>) : Flow<String?>

    fun observeBoolean(key: Preferences.Key<Boolean>) : Flow<Boolean>

    suspend fun setTokens(response: LotteryInfoResponse?)

    suspend fun removeTokens()

    suspend fun setLotteryInfo(response: LotteryInfoModel?)

    companion object {
        const val DATA_STORE_NAME = "DataStore"
    }
}