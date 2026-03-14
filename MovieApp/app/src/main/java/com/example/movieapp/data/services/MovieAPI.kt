package com.example.movieapp.data.services

import com.example.movieapp.data.dto.MovieDTO
import com.example.movieapp.DTOs.MovieListDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieAPI {

    // api queries
    @GET("movie/{category}")
    suspend fun getMoviesList(
        @Path("category") category: String,
        @Query("page") page: Int
    ): MovieListDTO

    @GET("movie/{id}")
    suspend fun detailsById(
        @Path("id") movieId: Int
    ): MovieDTO

    @GET("search/movie")
    suspend fun searchByTitle(
        @Query("query") title: String,
        @Query("page") page: Int
    ): MovieListDTO
}