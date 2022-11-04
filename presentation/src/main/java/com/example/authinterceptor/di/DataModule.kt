package com.example.authinterceptor.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.data.local.AppDataStore
import com.example.data.local.AppDataStoreImpl
import com.example.data.remote.api_handler.ApiHandler
import com.example.data.remote.api_handler.ApiHandlerImpl
import com.example.data.remote.services.lottery.LotteryInfoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile(name = AppDataStore.DATA_STORE_NAME)
    }

    @Singleton
    @Provides
    fun provideAppDataStore(
        dataStore: DataStore<Preferences>
    ): AppDataStore = AppDataStoreImpl(dataStore = dataStore)

    @Singleton
    @Provides
    fun provideOkHttpClient(

    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("BASE_URL")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideUserInfoService(
        retrofit: Retrofit
    ): LotteryInfoApi = retrofit.create(LotteryInfoApi::class.java)

    @Singleton
    @Provides
    fun provideApiHandler(): ApiHandler = ApiHandlerImpl()
}