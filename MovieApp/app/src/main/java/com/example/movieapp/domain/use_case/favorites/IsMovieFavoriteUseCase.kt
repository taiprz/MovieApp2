package com.example.movieapp.domain.use_case.favorites

import com.example.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class IsMovieFavoriteUseCase @Inject constructor(
  private val  movierepo : MovieRepository
) {
    suspend operator fun invoke(id: Int): Boolean {
        return movierepo.getMovieByIdFromDB(id)
    }
}