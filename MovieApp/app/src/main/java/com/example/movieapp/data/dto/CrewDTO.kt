package com.example.movieapp.data.dto


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CrewDTO(
    @SerializedName("adult")
    val adult: Boolean? = null,
    @SerializedName("credit_id")
    val creditId: String? = null,
    @SerializedName("department")
    val department: String? = null,
    @SerializedName("gender")
    val gender: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("job")
    val job: String? = null,
    @SerializedName("known_for_department")
    val knownForDepartment: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("original_name")
    val originalName: String? = null,
    @SerializedName("popularity")
    val popularity: Double? = null,
    @SerializedName("profile_path")
    val profilePath: String? = null
)