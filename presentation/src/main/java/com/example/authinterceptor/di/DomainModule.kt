package com.example.authinterceptor.di

import com.example.data.datasource.LotteryInfoRepositoryImpl
import com.example.domain.repository.LotteryInfoRepository
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
