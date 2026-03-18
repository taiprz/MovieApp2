package com.example.movieapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.movieapp.domain.use_case.movies.GetNowPlayingUseCase
import com.example.movieapp.domain.use_case.movies.GetPopularUseCase
import com.example.movieapp.domain.use_case.movies.GetTopRatedUseCase
import com.example.movieapp.domain.use_case.movies.GetUpcomingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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