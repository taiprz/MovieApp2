package com.example.movieapp.data.dto


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsDTO(
    @SerializedName("cast")
    val cast: List<CastDTO?>? = null,
    @SerializedName("crew")
    val crew: List<CrewDTO?>? = null,
    @SerializedName("id")
    val id: Int? = null
)