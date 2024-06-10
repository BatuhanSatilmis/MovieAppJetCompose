package com.kotlin.batuhan.movieappjetcompose.movieList.data.local.movie

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class],
    version = 1
 //   exportSchema = true
)
abstract class MovieDatabase: RoomDatabase() {

    abstract val movieDao: MovieDao
}