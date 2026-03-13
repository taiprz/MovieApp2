package com.example.movieapp.data.utils

import android.os.Bundle
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


// screens we will be using
@Serializable
sealed interface Route: NavKey {

    @Serializable
    data object Home : Route
    @Serializable
    data class Details(val id: Int) : Route

    fun Route.Details.toBundle(): Bundle = Bundle().apply {
        putInt("id", id)
    }

    @Serializable
    data object Favorites : Route
}