package com.example.domain.usecase

import com.example.domain.core.Result
import com.example.domain.models.ui.LotteryInfoModel
import com.example.domain.repository.LotteryInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetLotteryInfoUseCase @Inject constructor(
    private val userInfoRepository: LotteryInfoRepository
) {
    suspend operator fun invoke(): Result<LotteryInfoModel> =
        withContext(Dispatchers.IO) {
            userInfoRepository.getUserInfo()
        }
}