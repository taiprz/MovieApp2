package com.example.movieapp.data.mappers

import com.example.movieapp.data.dto.CastDTO
import com.example.movieapp.data.dto.CreditsDTO
import com.example.movieapp.data.dto.CrewDTO
import com.example.movieapp.domain.model.Credits
import kotlin.collections.emptyList
import com.example.movieapp.data.*

fun CreditsDTO.toCredit(): Credits {
    return Credits(
        cast = cast?.mapNotNull { it?.toCast() } ?: emptyList(),
        crew = crew?.mapNotNull { it?.toCrew() } ?: emptyList(),
        id = id ?: -1
    )
}