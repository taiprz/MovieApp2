package com.example.movieapp.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.data.mappers.toMovie
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.data.services.MovieAPI


class MovieDataPagingSource(
    private val api: MovieAPI,
    private val query: String,
    private val category: String,
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1

        return try {
            val response = if (query.isNotEmpty()) {
                api.searchByTitle(query, page)
            } else {
                api.getMoviesList(category, page)
            }

            val movieDTO = response.results
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = response.totalPages?.let { if (page < it) page + 1 else null }

            LoadResult.Page(
                data = movieDTO?.mapNotNull { it?.toMovie(category) } ?: emptyList(),
                prevKey = prevKey,
                nextKey = nextKey
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}