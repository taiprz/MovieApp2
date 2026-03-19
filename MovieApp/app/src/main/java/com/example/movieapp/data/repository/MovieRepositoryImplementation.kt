package com.example.movieapp.data.repository

import com.example.movieapp.DAO.MovieDAO
import com.example.movieapp.data.services.MovieAPI
import com.example.movieapp.data.utils.Category
import com.example.movieapp.data.mappers.toMovie
import com.example.movieapp.data.mappers.toMovieEntity
import com.example.movieapp.data.utils.Result
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class MovieRepositoryImplementation @Inject constructor(
    private val movieAPI: MovieAPI,
    private val movieDao: MovieDAO
) : MovieRepository {

    // TODO: FIND BETTER IMPLEMENTATION
    override suspend fun getMovieByIdFromApi(id: Int): Flow<Result<Movie>> = flow {
        emit(Result.Loading(true))
        try {
            val movieFromApi = movieAPI.detailsById(id)
            val movie = movieFromApi.toMovie("details")
            emit(Result.Success(movie))
        } catch (e: IOException) {
            emit(Result.Error("Connection error."))
        } catch (e: Exception) {
            emit(Result.Error("Error: ${e.localizedMessage}"))
        } finally {
            emit(Result.Loading(false))
        }
    }

    override suspend fun getMovieByIdFromDB(id: Int): Boolean {
        return movieDao.getMovieById(id) != null
    }

    override suspend fun addFavorite(movie: Movie) {
        movieDao.insert(movie.toMovieEntity(Category.FAVORITES))
    }

    override suspend fun removeFavorite(movieId: Int) {
        movieDao.deleteById(movieId)
    }

}