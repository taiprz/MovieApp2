package com.example.movieapp.di

import com.example.movieapp.data.repository.AuthRepositoryImpl
import com.example.movieapp.data.source.FirebaseAuthDataSource
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseAuthDataSource(firebaseAuth: FirebaseAuth): FirebaseAuthDataSource {
        return FirebaseAuthDataSource(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideAuthRepositoryImpl(dataSource: FirebaseAuthDataSource): AuthRepositoryImpl {
        return AuthRepositoryImpl(dataSource)
    }
}