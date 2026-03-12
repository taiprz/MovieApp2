package com.example.movieapp.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.DAO.MovieDAO
import com.example.movieapp.data.source.MovieDataPagingSource
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.data.services.MovieAPI
import com.example.movieapp.data.utils.Category
import com.example.movieapp.data.utils.toMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


// TODO: CONVERT NOT SUSPEND FUNCTIONS TO SUSPEND WHEN NEEDED
class MovieListRepositoryImplementation @Inject constructor(
    private val movieAPI : MovieAPI,
    private val movieDao : MovieDAO
) : MovieListRepository {

    // CHANGES:
    // removed the suspend since flow is already asynchronous:

    override fun getFavorites(): Flow<List<Movie>> {
        return movieDao.getFavorites().map { entities ->
            entities.map { it.toMovie(Category.POPULAR) }
        }
    }

    override  fun getAllMovies(): Flow<PagingData<Movie>> {
       return Pager(config = PagingConfig(
           pageSize = MAX_ITEMS,
           prefetchDistance = PREFETCH_ITEMS),
           pagingSourceFactory = { MovieDataPagingSource(movieAPI, "") }
       ).flow
    }

    override fun searchMoviesPaged(
        query: String,
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(MAX_ITEMS),
            pagingSourceFactory = { MovieDataPagingSource(movieAPI, query) }
        ).flow
    }

    private companion object {
        const val MAX_ITEMS = 20
        const val PREFETCH_ITEMS = 3
    }
}




