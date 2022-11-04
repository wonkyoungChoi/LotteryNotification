package com.example.domain.usecase

import com.example.domain.core.Result
import com.example.domain.models.requests.LotteryInfoRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(loginRequest: LotteryInfoRequest): Result<Unit> =
        withContext(Dispatchers.IO) {
            with(loginRequest) {
                if (username.isBlank())
                    return@withContext Result.Error("Username is empty")

                if (password.isBlank())
                    return@withContext Result.Error("Password is empty")
            }

            authRepository.login(loginRequest)
        }
}