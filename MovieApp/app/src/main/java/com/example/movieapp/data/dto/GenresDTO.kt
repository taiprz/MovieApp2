package com.example.movieapp.data.dto


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresDTO(
    @SerializedName("genres")
    val genres: List<GenreDTO?>? = null
)