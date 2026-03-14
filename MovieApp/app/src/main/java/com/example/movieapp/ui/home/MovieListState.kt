package com.example.movieapp.ui.home

import com.example.movieapp.domain.model.Movie

data class MovieListState(
    val isLoading: Boolean = false,
    val favoriteMovieList : List<Movie> = emptyList()
)