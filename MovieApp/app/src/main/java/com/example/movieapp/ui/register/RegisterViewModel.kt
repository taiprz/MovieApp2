package com.example.movieapp.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.User
import com.example.movieapp.domain.use_case.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
   private val register: RegisterUseCase
) : ViewModel() {
    fun register(email: String, password: String, onResult: (Result<User>) -> Unit) {
        viewModelScope.launch {
            try {
                val user = register(email, password)
                onResult(Result.success(user))
            } catch (e: Exception) {
                onResult(Result.failure(e))
            }
        }
    }
}