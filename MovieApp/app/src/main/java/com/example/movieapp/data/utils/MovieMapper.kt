package com.example.movieapp.data.utils

import com.example.movieapp.data.dto.MovieDTO
import com.example.movieapp.entities.MovieEntity
import com.example.movieapp.domain.model.Movie
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.collections.emptyList
import kotlin.text.split


// CHANGES: Mapped ReleaseDate format to fit the country
// Deleted unnecessary mapper (Dto to Entity)

fun mapDates(date: String?): String {
    if (date.isNullOrEmpty()) return ""

    return try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val localDate = LocalDate.parse(date, inputFormatter)

        val outputFormatter = DateTimeFormatter
            .ofPattern("dd MMM yyyy")
            .withLocale(Locale.getDefault())

        localDate.format(outputFormatter)
    } catch (e: Exception) {
        // If already formated, return as it is to avoid crashes
        date
    }
}

fun MovieEntity.toMovie(
    category: String
): Movie {
    return Movie(
        adult = adult,
        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = mapDates(releaseDate),
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        video = video,
        id = id,
        originalTitle = originalTitle,
        category = category,
        genreIds = genreIds.split(",").filter { it.isNotEmpty() })
}

fun MovieDTO.toMovie(
    category: String
): Movie {
    return Movie(
        adult = adult ?: false,
        backdropPath = backdropPath ?: "",
        originalLanguage = originalLanguage ?: "",
        overview = overview ?: "",
        posterPath = posterPath ?: "",
        releaseDate = mapDates(releaseDate),
        title = title ?: "",
        voteAverage = voteAverage ?: 0.0,
        popularity = popularity ?: 0.0,
        voteCount = voteCount ?: 0,
        video = video ?: false,
        id = id ?: -1,
        originalTitle = originalTitle ?: "",
        category = category,
        genreIds = genreIds?.map { it.toString() } ?: emptyList()
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


