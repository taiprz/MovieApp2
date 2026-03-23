package com.example.movieapp.domain.model

import com.example.movieapp.data.dto.CastDTO
import com.example.movieapp.data.dto.CrewDTO
import com.google.gson.annotations.SerializedName

data class Credits(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)
