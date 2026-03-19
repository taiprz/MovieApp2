package com.example.movieapp.di

import com.example.movieapp.domain.repository.MovieRepository
import com.example.movieapp.data.repository.MovieRepositoryImplementation
import com.example.movieapp.domain.repository.MovieListRepository
import com.example.movieapp.data.repository.MovieListRepositoryImplementation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMovieListRepository(
        movieListRepositoryImp : MovieListRepositoryImplementation
    ) : MovieListRepository

    @Binds
    abstract fun bindMovieRepository(
        impl: MovieRepositoryImplementation
    ): MovieRepository
}

