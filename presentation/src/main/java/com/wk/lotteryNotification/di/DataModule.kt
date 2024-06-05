package com.wk.lotteryNotification.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.wk.data.data_storage.database.AlarmDao
import com.wk.data.data_storage.database.AlarmDatabase
import com.wk.data.remote.api_handler.ApiHandler
import com.wk.data.remote.api_handler.ApiHandlerImpl
import com.wk.data.remote.services.lottery.LotteryInfoApi
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
    fun provideOkHttpClient(
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()

    @Singleton
    var gson: Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://dhlottery.co.kr/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Singleton
    @Provides
    fun provideLotteryInfoService(
        retrofit: Retrofit
    ): LotteryInfoApi = retrofit.create(LotteryInfoApi::class.java)

    @Singleton
    @Provides
    fun provideApiHandler(): ApiHandler = ApiHandlerImpl()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AlarmDatabase {
        return Room
            .databaseBuilder(context, AlarmDatabase::class.java, "note_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideAlarmDao(alarmDatabase: AlarmDatabase): AlarmDao {
        return alarmDatabase.alarmDao()
    }
}