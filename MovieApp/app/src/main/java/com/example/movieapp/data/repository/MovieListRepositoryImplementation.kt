package com.example.movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.DAO.MovieDAO
import com.example.movieapp.data.services.MovieAPI
import com.example.movieapp.data.source.MovieDataPagingSource
import com.example.movieapp.data.utils.Category
import com.example.movieapp.data.utils.toMovie
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.repository.MovieListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

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

    override fun getUpcoming(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = MAX_ITEMS,
                prefetchDistance = PREFETCH_ITEMS
            ),
            pagingSourceFactory = { MovieDataPagingSource(movieAPI, "", Category.UPCOMING) }
        ).flow
    }

    override fun getNowPlaying(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = MAX_ITEMS,
                prefetchDistance = PREFETCH_ITEMS
            ),
            pagingSourceFactory = { MovieDataPagingSource(movieAPI, "", Category.NOWPLAYING) }
        ).flow
    }

    override fun getTopRated(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = MAX_ITEMS,
                prefetchDistance = PREFETCH_ITEMS
            ),
            pagingSourceFactory = { MovieDataPagingSource(movieAPI, "", Category.TOPRATED) }
        ).flow
    }

    override  fun getPopularMovies(): Flow<PagingData<Movie>> {
       return Pager(
           config = PagingConfig(
               pageSize = MAX_ITEMS,
               prefetchDistance = PREFETCH_ITEMS
           ),
           pagingSourceFactory = { MovieDataPagingSource(movieAPI, "", Category.POPULAR) }
       ).flow
    }

    override fun searchMoviesPaged(
        query: String,
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(MAX_ITEMS),
            pagingSourceFactory = { MovieDataPagingSource(movieAPI, query, "") }
        ).flow
    }

    private companion object {
        const val MAX_ITEMS = 20
        const val PREFETCH_ITEMS = 5
    }
}