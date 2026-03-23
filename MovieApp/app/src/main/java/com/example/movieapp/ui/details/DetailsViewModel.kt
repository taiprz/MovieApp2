package com.example.movieapp.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.utils.Result
import com.example.movieapp.domain.model.Credits
import com.example.movieapp.domain.model.Movie
//import com.example.movieapp.data.utils.Resource
import com.example.movieapp.domain.use_case.favorites.AddFavoriteUseCase
import com.example.movieapp.domain.use_case.favorites.IsMovieFavoriteUseCase
import com.example.movieapp.domain.use_case.favorites.RemoveFavoriteUseCase
import com.example.movieapp.domain.use_case.movies.GetCreditsUseCase
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
    private val removeFavorite: RemoveFavoriteUseCase,
    private val getCredits: GetCreditsUseCase
) : ViewModel() {

    private val _detailState = MutableStateFlow(DetailState())
    val detailsState = _detailState.asStateFlow()

    private val _creditsState = MutableStateFlow(CreditsState())

    val creditsState = _creditsState.asStateFlow()


    fun getMovie(id: Int) {
        viewModelScope.launch {
            getMovieDetail(id).collect { result ->
                when (result) {
                    is Result.Loading -> _detailState.update {
                        it.copy(isLoading = true)
                    }

                    is Result.Success -> _detailState.update {
                        it.copy(
                            isLoading = false,
                            movie = result.data,
                            isFavorite = isMovieFavorite(id)
                        )
                    }

                    is Result.Error -> _detailState.update {
                        it.copy(isLoading = false)
                    }
                }
            }
        }
    }

    fun addToFavorites(movie: Movie) {
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

    fun getMovieCredits(id: Int) {
        viewModelScope.launch {
            getCredits(id).collect { result ->
                when (result) {
                    is Result.Loading -> _creditsState.update {
                        it.copy(isLoading = true)
                    }

                    is Result.Success -> _creditsState.update {
                        it.copy(
                            isLoading = false,
                            credits = result.data,
                        )
                    }

                    is Result.Error -> _creditsState.update {
                        it.copy(isLoading = false)
                    }
                }
            }
        }
    }
}