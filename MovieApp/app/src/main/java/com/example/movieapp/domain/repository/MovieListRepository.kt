package com.example.movieapp.domain.repository

import androidx.paging.PagingData
import com.example.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {

    fun getFavorites(): Flow<List<Movie>>

    fun getUpcoming(): Flow<PagingData<Movie>>

    fun searchMoviesPaged(query: String,
    ): Flow<PagingData<Movie>>

    fun getPopularMovies() : Flow<PagingData<Movie>>

    fun getNowPlaying(): Flow<PagingData<Movie>>
    fun getTopRated(): Flow<PagingData<Movie>>
}




