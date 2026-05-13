package com.ratanapps.auth.domain.usecase

import com.ratanapps.auth.domain.repo.AuthRepository
import jakarta.inject.Inject

class LogoutUseCase @Inject constructor(val authRepository: AuthRepository) {

    suspend operator fun invoke() {
        authRepository.logout()
    }
}