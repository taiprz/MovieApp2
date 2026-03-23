package com.example.movieapp.data.mappers

import com.example.movieapp.BuildConfig
import com.example.movieapp.data.dto.CrewDTO
import com.example.movieapp.domain.model.Crew

fun CrewDTO.toCrew() : Crew {
    return Crew(
        adult = adult ?: false,
        creditId = creditId ?: "",
        department = department ?: "",
        gender = gender ?: -1,
        id = id ?: -1,
        job = job ?: "",
        knownForDepartment = knownForDepartment ?: "",
        name = name ?: "",
        originalName = originalName ?: "",
        popularity = popularity ?: 0.0,
        profilePath = (BuildConfig.BASE_IMAGE_URL + profilePath)
    )
}