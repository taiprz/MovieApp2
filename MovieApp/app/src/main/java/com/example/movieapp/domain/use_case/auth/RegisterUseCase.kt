package com.example.movieapp.domain.use_case.auth

import com.example.movieapp.domain.model.User
import com.example.movieapp.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepo : AuthRepository
) {
     suspend operator fun invoke(email: String, password : String) : User {
         return authRepo.register(email, password)
     }
}