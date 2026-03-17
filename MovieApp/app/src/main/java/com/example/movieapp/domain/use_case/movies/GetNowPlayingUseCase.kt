package com.example.movieapp.domain.use_case.movies

import com.example.movieapp.domain.repository.MovieListRepository
import javax.inject.Inject

class GetNowPlayingUseCase @Inject constructor(
    private val movierepo : MovieListRepository
){
    operator fun invoke() = movierepo.getNowPlaying()
}