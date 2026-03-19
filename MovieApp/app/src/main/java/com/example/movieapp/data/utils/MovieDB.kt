package com.example.movieapp.data.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.DAO.MovieDAO
import com.example.movieapp.data.local.entities.MovieEntity
@Database (
    entities = [MovieEntity::class],
    version = 1
)
abstract class MovieDB : RoomDatabase() {
    abstract val movieDao : MovieDAO
}