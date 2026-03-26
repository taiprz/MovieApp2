package com.example.movieapp.data.repository

import com.example.movieapp.data.source.FirebaseAuthDataSource
import com.example.movieapp.domain.model.User
import com.example.movieapp.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val dataSource: FirebaseAuthDataSource
) : AuthRepository {

    override suspend fun login(email: String, password: String): User {
        val result = dataSource.login(email, password)
        val firebaseUser = result.user!!

        return User(
            uid = firebaseUser.uid,
            email = firebaseUser.email
        )
    }

    override suspend fun register(email: String, password: String): User {
        val result = dataSource.register(email, password)
        val firebaseUser = result.user!!

        return User(
            uid = firebaseUser.uid,
            email = firebaseUser.email
        )
    }

    override fun logout() = dataSource.logout()

    override fun getCurrentUser(): User? {
        return dataSource.getCurrentUser()?.let {
            it.email?.let { email -> User(it.uid, email) }
        }
    }
}