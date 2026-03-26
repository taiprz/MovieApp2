package com.example.movieapp.domain.use_case.auth

import com.example.movieapp.domain.model.User
import com.example.movieapp.domain.repository.AuthRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val authRepo : AuthRepository
) {
    suspend operator fun invoke() : User? {
       return authRepo.getCurrentUser()
    }
}