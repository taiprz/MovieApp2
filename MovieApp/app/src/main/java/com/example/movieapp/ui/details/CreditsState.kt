package com.example.movieapp.ui.details

import com.example.movieapp.domain.model.Cast
import com.example.movieapp.domain.model.Credits
import com.example.movieapp.domain.model.Crew

data class CreditsState(
    val isLoading : Boolean = false,
    val credits : Credits? = null,
    val crew : Crew?  = null,
    val cast : Cast? = null
)
