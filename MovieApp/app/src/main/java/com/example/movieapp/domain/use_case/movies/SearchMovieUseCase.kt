package com.example.movieapp.domain.use_case.movies

import com.example.movieapp.domain.repository.MovieListRepository
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val movieRepo: MovieListRepository
) {
    operator fun invoke(title: String) = movieRepo.searchMoviesPaged(title)
}