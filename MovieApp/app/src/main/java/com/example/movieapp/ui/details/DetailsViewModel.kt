package com.example.movieapp.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.repository.MovieRepository
import com.example.movieapp.domain.use_cases.PosterUseCase
import com.example.movieapp.data.utils.Resource
import com.example.movieapp.domain.repository.MovieListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val posterUseCase: PosterUseCase,
    private val savedStateHandle : SavedStateHandle,
    private val movieListRepository: MovieListRepository
) : ViewModel() {

    // CHANGES: Implemented savedStateHandle and changed logic to init
    // added logic to the charge itself to see if favorite
    private val movieId: Int = savedStateHandle["movieId"] ?: 0
    private val _detailState = MutableStateFlow(DetailState())
    val detailsState = _detailState.asStateFlow()

    init {
        getMovie(movieId)
    }

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

    fun loadPoster(movie : Movie): String {
        return posterUseCase.loadPoster(movie)
    }
}