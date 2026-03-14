package com.example.movieapp.ui.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.repository.MovieRepository
import com.example.movieapp.domain.repository.MovieListRepository
import com.example.movieapp.ui.home.MovieListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class FavoritesViewModel@Inject constructor(
    private val movieListRepository: MovieListRepository,
    private val movieRepository: MovieRepository
) : ViewModel() {

    // CHANGES: Moved EditMode logic from Screen to ViewModel
    private val _editMode = MutableStateFlow(false)
    val editMode = _editMode.asStateFlow()

    private var _movieListState = MutableStateFlow(MovieListState())
    val movieListState = _movieListState

    init {
        getFavoriteMoviesList()
    }

    private fun getFavoriteMoviesList() {
        viewModelScope.launch {
            _movieListState.update { it.copy(isLoading = true) }

            movieListRepository.getFavorites().collectLatest { favoriteList ->
                _movieListState.update {
                    it.copy(
                        favoriteMovieList = favoriteList,
                        isLoading = false
                    )
                }

                Log.v("Tai", "$_movieListState")
            }
        }
    }

    fun removeFavorite(movie: Movie) {
        viewModelScope.launch {
            movieRepository.removeFavorite(movie.id)
        }
    }

    fun toggleEditMode() {
        _editMode.update { !it }
    }
}