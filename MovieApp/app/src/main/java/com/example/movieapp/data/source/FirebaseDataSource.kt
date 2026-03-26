package com.example.movieapp.data.source

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseAuthDataSource(
    private val firebaseAuth: FirebaseAuth
) {

    suspend fun login(email: String, password: String) =
        firebaseAuth.signInWithEmailAndPassword(email, password).await()

    suspend fun register(email: String, password: String) =
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()

    fun getCurrentUser() = firebaseAuth.currentUser

    fun logout() = firebaseAuth.signOut()
}