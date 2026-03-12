package com.example.movieapp.domain.use_cases

import com.example.movieapp.domain.model.Movie
import com.example.movieapp.data.services.MovieAPI
import javax.inject.Inject

class PosterUseCase @Inject constructor() {

    private val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"

    // TODO: TRANSFER BUSINESS LOGIC FROM VIEWMODEL AND IMPLEMENT HERE  
    fun loadPoster(movie : Movie): String {

        return BASE_IMAGE_URL + movie.posterPath
    }
}