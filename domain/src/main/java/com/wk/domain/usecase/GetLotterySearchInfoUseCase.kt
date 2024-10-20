package com.wk.domain.usecase

import com.wk.domain.core.Result
import com.wk.domain.models.ui.LotteryInfoModel
import com.wk.domain.repository.LotteryInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetLotterySearchInfoUseCase @Inject constructor(
    private val lotteryInfoRepository: LotteryInfoRepository
) {
    suspend operator fun invoke(type: String, drwNo: String): Result<LotteryInfoModel> =
        withContext(Dispatchers.IO) {
            lotteryInfoRepository.getLotterySearchInfo(type, drwNo)
        }
}