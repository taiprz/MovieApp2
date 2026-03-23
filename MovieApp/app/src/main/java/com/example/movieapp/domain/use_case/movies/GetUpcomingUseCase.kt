package com.example.movieapp.domain.use_case.movies

import com.example.movieapp.domain.repository.MovieListRepository
import javax.inject.Inject

class GetUpcomingUseCase @Inject constructor(
    private val movieRepo: MovieListRepository
) {
     operator fun invoke() = movieRepo.getUpcoming()
}