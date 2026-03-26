package com.example.movieapp.data.utils

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey {

    @Serializable
    data object Login : Route
    @Serializable
    data object Home : Route

    @Serializable
    data object Search : Route
    @Serializable
    data class Details(val id: Int) : Route

    @Serializable
    data object Favorites : Route
}