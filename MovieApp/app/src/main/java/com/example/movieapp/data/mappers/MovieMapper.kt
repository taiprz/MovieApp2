package com.example.movieapp.data.mappers

import com.example.movieapp.BuildConfig
import com.example.movieapp.core.extensions.toDateFormatted
import com.example.movieapp.data.dto.MovieDTO
import com.example.movieapp.data.local.entities.MovieEntity
import com.example.movieapp.domain.model.Genre
import com.example.movieapp.domain.model.Movie
import kotlin.collections.emptyList
import kotlin.text.split

fun MovieEntity.toMovie(
    category: String
): Movie {
    return Movie(
        adult = adult,
        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        video = video,
        id = id,
        originalTitle = originalTitle,
        category = category,
        genreIds = genreIds.split(",").map { it.toInt() }
    )
}

fun MovieDTO.toMovie(
    category: String
): Movie {
    return Movie(
        adult = adult ?: false,
        backdropPath = (BuildConfig.BASE_IMAGE_URL + backdropPath),
        originalLanguage = originalLanguage ?: "",
        overview = overview ?: "",
        posterPath = (BuildConfig.BASE_IMAGE_URL + posterPath),
        releaseDate = releaseDate.toDateFormatted(),
        title = title ?: "",
        voteAverage = voteAverage ?: 0.0,
        popularity = popularity ?: 0.0,
        voteCount = voteCount ?: 0,
        video = video ?: false,
        id = id ?: -1,
        originalTitle = originalTitle ?: "",
        category = category,
        genreIds = genreIds.orEmpty().mapNotNull { it }
    )
}

fun Movie.toMovieEntity(
    category: String
): MovieEntity {
    return MovieEntity(
        adult = adult,
        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        video = video,
        id = id,
        originalTitle = originalTitle,
        category = category,
        genreIds = genreIds.joinToString(",")
    )
}

fun Movie.mapGenres(genres: List<Genre>): List<Genre> {
    val genreMap = genres.associateBy { it.id }
    return genreIds.mapNotNull { genreMap[it] }
}


