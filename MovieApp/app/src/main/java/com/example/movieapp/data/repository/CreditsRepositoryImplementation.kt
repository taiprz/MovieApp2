package com.example.movieapp.data.repository

import com.example.movieapp.data.mappers.toCredit
import com.example.movieapp.data.services.MovieAPI
import com.example.movieapp.data.utils.Result
import com.example.movieapp.domain.model.Credits
import com.example.movieapp.domain.repository.CreditsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class CreditsRepositoryImplementation @Inject constructor(
   private val movieAPI: MovieAPI
): CreditsRepository {
    override fun getCredits(id : Int): Flow<Result<Credits>> = flow {
        emit(Result.Loading(true))
        try {
            val creditsDTO = movieAPI.getCredits(id)
            val credits = creditsDTO.toCredit()
            emit(Result.Success(credits))
        } catch (e: IOException) {
            emit(Result.Error("Connection error."))
        } catch (e: Exception) {
            emit(Result.Error("Error: ${e.localizedMessage}"))
        } finally {
            emit(Result.Loading(false))
        }
    }
}