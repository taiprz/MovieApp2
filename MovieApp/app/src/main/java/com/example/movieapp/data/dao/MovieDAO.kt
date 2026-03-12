package com.example.movieapp.DAO

import androidx.room.*
import com.example.movieapp.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {

    // database queries

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntity)

    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity?

    @Query("SELECT * FROM MovieEntity")
    fun getFavorites(): Flow<List<MovieEntity>>

    @Query("DELETE FROM MovieEntity WHERE id = :movieId")
    suspend fun deleteById(movieId: Int)

}