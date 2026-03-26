package com.example.movieapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.User
import com.example.movieapp.domain.use_case.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

    @HiltViewModel
    class LoginViewModel @Inject constructor(
        private val login: LoginUseCase
    ) : ViewModel() {

        fun login(email: String, password: String, onResult: (Result<User>) -> Unit) {
            viewModelScope.launch {
                try {
                    val user = login(email, password)
                    onResult(Result.success(user))
                } catch (e: Exception) {
                    onResult(Result.failure(e))
                }
            }
        }
    }

