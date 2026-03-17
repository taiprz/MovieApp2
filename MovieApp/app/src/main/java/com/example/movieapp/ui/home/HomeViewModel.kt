package com.example.movieapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.use_case.movies.GetNowPlayingUseCase
import com.example.movieapp.domain.use_case.movies.GetPopularUseCase
import com.example.movieapp.domain.use_case.movies.GetTopRatedUseCase
import com.example.movieapp.domain.use_case.movies.GetUpcomingUseCase
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
    getUpcoming : GetUpcomingUseCase,
    getNowPlaying: GetNowPlayingUseCase,
    getTopRated: GetTopRatedUseCase
) : ViewModel() {
    val popularMovies = getPopular()
    val upcomingMovies = getUpcoming()

    val nowPlayingMovies = getNowPlaying()

    val topRatedMovies = getTopRated()
}