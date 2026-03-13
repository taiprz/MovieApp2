package com.example.movieapp.ui.details

import android.util.Log
import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.repository.MovieRepository
import com.example.movieapp.data.utils.Resource
import com.example.movieapp.data.utils.Route
import com.example.movieapp.domain.repository.MovieListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _detailState = MutableStateFlow(DetailState())
    val detailsState = _detailState.asStateFlow()

     fun getMovie(id: Int) {
        viewModelScope.launch {
            movieRepository.getMovieByIdFromApi(id).collect { result ->
                when (result) {
                    is Resource.Loading -> _detailState.update {
                        it.copy(isLoading = true)
                    }
                    is Resource.Success -> _detailState.update {
                        it.copy(
                            isLoading = false,
                            movie = result.data,
                            isFavorite = movieRepository.getMovieByIdFromDB(id)
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
            movieRepository.addFavorite(movie)
            _detailState.update { it.copy(isFavorite = true) }
        }
    }

    fun removeFavorite(movie: Movie) {
        viewModelScope.launch {
            movieRepository.removeFavorite(movie.id)
            _detailState.update { it.copy(isFavorite = false) }
        }
    }
}