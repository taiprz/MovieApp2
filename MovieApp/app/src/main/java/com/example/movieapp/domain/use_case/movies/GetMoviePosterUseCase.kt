package com.example.movieapp.domain.use_case.movies

import com.example.movieapp.R
import com.example.movieapp.domain.model.Movie

class GetMoviePosterUseCase(
    private val defaultPosterUrl: Any = R.drawable.ic_no_image
) {
    operator fun invoke(movie: Movie): Any {
        return movie.posterPath ?: defaultPosterUrl
    }
}