package com.example.movieapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.data.utils.Resource
import com.example.movieapp.domain.use_case.favorites.AddFavoriteUseCase
import com.example.movieapp.domain.use_case.favorites.IsMovieFavoriteUseCase
import com.example.movieapp.domain.use_case.favorites.RemoveFavoriteUseCase
import com.example.movieapp.domain.use_case.movies.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetail: GetMovieDetailUseCase,
    private val isMovieFavorite: IsMovieFavoriteUseCase,
    private val addFavorite: AddFavoriteUseCase,
    private val removeFavorite: RemoveFavoriteUseCase
) : ViewModel() {

    private val _detailState = MutableStateFlow(DetailState())
    val detailsState = _detailState.asStateFlow()

     fun getMovie(id: Int) {
        viewModelScope.launch {
            getMovieDetail(id).collect { result ->
                when (result) {
                    is Resource.Loading -> _detailState.update {
                        it.copy(isLoading = true)
                    }
                    is Resource.Success -> _detailState.update {
                        it.copy(
                            isLoading = false,
                            movie = result.data,
                            isFavorite = isMovieFavorite(id)
                        )
                    }
                    is Resource.Error -> _detailState.update {
                        it.copy(isLoading = false)
                    }
                }
            }
        }
    }

    fun addToFavorites(movie: Movie)  {
        viewModelScope.launch {
            addFavorite(movie)
            _detailState.update { it.copy(isFavorite = true) }
        }
    }

    fun removeFavorite(movie: Movie) {
        viewModelScope.launch {
            removeFavorite(movie.id)
            _detailState.update { it.copy(isFavorite = false) }
        }
    }
}