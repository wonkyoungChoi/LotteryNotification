package com.wk.lotteryNotification.di

import com.wk.data.datasource.LotteryInfoRepositoryImpl
import com.wk.domain.repository.LotteryInfoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {
    @Singleton
    @Binds
    fun bindLotteryInfoRepository(lotteryInfoRepositoryImpl: LotteryInfoRepositoryImpl): LotteryInfoRepository
}
