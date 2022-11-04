package com.wk.data.local

import androidx.datastore.preferences.core.Preferences
import com.wk.domain.models.ui.LotteryInfoModel
import kotlinx.coroutines.flow.Flow

interface AppDataStore {

    suspend fun putValue(key: Preferences.Key<String>, value: String)

    suspend fun putValue(key: Preferences.Key<Boolean>, value: Boolean)

    fun observeString(key: Preferences.Key<String>) : Flow<String?>

    fun observeBoolean(key: Preferences.Key<Boolean>) : Flow<Boolean>

    suspend fun setLotteryInfo(response: LotteryInfoModel?)

    companion object {
        const val DATA_STORE_NAME = "DataStore"
    }
}