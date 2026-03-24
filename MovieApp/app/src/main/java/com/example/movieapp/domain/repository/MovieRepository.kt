package com.example.movieapp.domain.repository

import com.example.movieapp.domain.model.Movie
import com.example.movieapp.data.utils.Result
import com.example.movieapp.domain.model.Genre
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovieByIdFromApi(id: Int): Flow<Result<Movie>>

    suspend fun getMovieByIdFromDB(id: Int) : Boolean

    suspend fun addFavorite(movie: Movie)

    suspend fun removeFavorite(movieId: Int)

    suspend fun getGenres() : List<Genre>
}