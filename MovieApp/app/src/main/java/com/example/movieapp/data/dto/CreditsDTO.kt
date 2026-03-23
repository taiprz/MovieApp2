package com.example.movieapp.data.dto


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class CreditsDTO(
    val cast: List<CastDTO?>,
    val crew: List<CrewDTO?>,
    val id: Int
)