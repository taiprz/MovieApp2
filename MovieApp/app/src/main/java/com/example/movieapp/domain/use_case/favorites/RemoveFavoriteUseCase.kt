package com.example.movieapp.domain.use_case.favorites

import com.example.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class RemoveFavoriteUseCase @Inject constructor(
    private val movierepo: MovieRepository
) {
    suspend operator fun invoke(movieId: Int) {
       return movierepo.removeFavorite(movieId)
    }
}