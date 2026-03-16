package com.example.movieapp.domain.use_case.favorites

import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val movierepo: MovieRepository
) {

    suspend operator fun invoke(movie: Movie) {
        movierepo.addFavorite(movie)
    }
}