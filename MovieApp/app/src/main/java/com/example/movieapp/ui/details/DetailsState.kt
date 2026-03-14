package com.example.movieapp.ui.details

import com.example.movieapp.domain.model.Movie

// CHANGES: ADDED FAVORITE TO THE STATE ITSELF
data class DetailState(
    val isLoading: Boolean = false,
    val movie : Movie? = null,
    val isFavorite : Boolean = false
)
