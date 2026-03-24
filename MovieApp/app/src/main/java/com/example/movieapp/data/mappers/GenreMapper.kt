package com.example.movieapp.data.mappers

import com.example.movieapp.data.dto.GenreDTO
import com.example.movieapp.domain.model.Genre

fun GenreDTO.toGenre() : Genre {
    return Genre(
        id = id ?: -1,
        name = name ?: ""
    )
}

fun List<GenreDTO?>?.toGenres(): List<Genre> {
    return this.orEmpty().mapNotNull { dto ->
        dto?.let {
            Genre(
                id = it.id ?: return@let null,
                name = it.name ?: return@let null
            )
        }
    }
}