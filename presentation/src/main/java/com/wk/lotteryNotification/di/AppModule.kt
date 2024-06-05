package com.wk.lotteryNotification.di

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import com.wk.data.common.wrappers.MyNotificationManager
import com.wk.data.common.wrappers.MyNotificationManagerImpl
import com.wk.data.common.wrappers.NetworkConnectivityManager
import com.wk.data.common.wrappers.NetworkConnectivityManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager =
        context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager

    @Singleton
    @Provides
    fun provideNetworkConnectivityManager(
        connectivityManager: ConnectivityManager
    ): NetworkConnectivityManager = NetworkConnectivityManagerImpl(
        connectivityManager = connectivityManager
    )

    @Singleton
    @Provides
    fun provideAlarmManager(@ApplicationContext context: Context): AlarmManager {
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    @Singleton
    @Provides
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager =
        context.getSystemService(NotificationManager::class.java) as NotificationManager


    @Singleton
    @Provides
    fun provideMyNotificationManager(
        @ApplicationContext context: Context,
        notificationManager: NotificationManager
    ): MyNotificationManager = MyNotificationManagerImpl(
        context = context,
        notificationManager = notificationManager
    )
}
