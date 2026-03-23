package com.example.movieapp.domain.repository

import com.example.movieapp.data.utils.Result
import com.example.movieapp.domain.model.Credits
import com.example.movieapp.domain.model.Crew
import kotlinx.coroutines.flow.Flow

interface CreditsRepository {
    fun getCredits(id: Int): Flow<Result<Credits>>
}