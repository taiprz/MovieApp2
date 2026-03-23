package com.example.movieapp.domain.use_case.movies

import com.example.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieRepo: MovieRepository
) {
    suspend operator fun invoke(movieId: Int) = movieRepo.getMovieByIdFromApi(movieId)
}