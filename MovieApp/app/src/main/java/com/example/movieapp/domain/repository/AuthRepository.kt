package com.example.movieapp.domain.repository

import com.example.movieapp.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): User
    suspend fun register(email: String, password: String): User
    fun logout()
    fun getCurrentUser(): User?
}