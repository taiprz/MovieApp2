package com.example.movieapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.use_case.movies.SearchMovieUseCase
import com.example.movieapp.ui.home.MovieListEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
   private val searchMovie: SearchMovieUseCase
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()
    private val _hasSearched = MutableStateFlow(false)
    val hasSearched = _hasSearched.asStateFlow()
    @OptIn(FlowPreview::class)
    val moviesFound = _searchText
        .debounce(1000)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            _hasSearched.value = query.isNotBlank()
            if (query.isBlank()) {
                flowOf(PagingData.empty())
            } else {
                searchMovie(query)
            }
        }
        .cachedIn(viewModelScope)

    fun onEvent(event: MovieListEvents) {
        when (event) {
            is MovieListEvents.Search -> {
                _searchText.value = event.query
                _hasSearched.value = event.query.isNotBlank()
            }
        }
    }
}
