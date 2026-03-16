package com.example.movieapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.use_case.movies.GetPopularUseCase
import com.example.movieapp.domain.use_case.movies.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getPopular: GetPopularUseCase,
    private val searchMovie: SearchMovieUseCase
) : ViewModel() {

    val movies = getPopular()
    private val _moviesFound = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val moviesFound = _moviesFound.asStateFlow()
    private val _searchText = MutableStateFlow("")

    init {
        observeSearch()
    }


    fun onEvent(event: MovieListEvents) {
        when (event) {
            is MovieListEvents.Search -> {
                _searchText.update { event.query }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearch() {
        viewModelScope.launch {
            _searchText.debounce(500).collectLatest { query ->
                if (query.isNotBlank()) {
                    pagedResultsFromSearch(query)
                }
            }
        }
    }

    private fun pagedResultsFromSearch(title: String) {
        viewModelScope.launch {
            searchMovie(title)
                .collectLatest { pagingData ->
                    _moviesFound.value = pagingData
                }
        }
    }
}