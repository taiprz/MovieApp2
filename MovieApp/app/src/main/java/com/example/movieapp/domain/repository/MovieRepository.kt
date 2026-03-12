package com.example.movieapp.domain.repository

import com.example.movieapp.domain.model.Movie
import com.example.movieapp.data.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    // CHANGES: REMOVED EXISTS FUNCTION
    suspend fun getMovieByIdFromApi(id: Int): Flow<Resource<Movie>>

    suspend fun getMovieByIdFromDB(id: Int) : Boolean

    suspend fun addFavorite(movie: Movie)

    suspend fun removeFavorite(movieId: Int)



}