package com.example.movieapp.domain.use_case.favorites

import com.example.movieapp.domain.repository.MovieListRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val movierepo: MovieListRepository
) {
    operator fun invoke() = movierepo.getFavorites()
}