package com.example.movieapp.domain.use_case.movies

import com.example.movieapp.domain.repository.CreditsRepository
import javax.inject.Inject

class GetCreditsUseCase @Inject constructor(
    private val creditsRepo: CreditsRepository
) {
    suspend operator fun invoke(movieId: Int) = creditsRepo.getCredits(movieId)
}