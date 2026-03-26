package com.example.movieapp.domain.use_case.auth

import com.example.movieapp.domain.repository.AuthRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val authRepo: AuthRepository
) {
    suspend operator fun invoke() {
        authRepo.logout()
    }
}