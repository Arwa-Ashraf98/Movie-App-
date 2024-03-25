package com.example.arwa.movieapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.arwa.movieapp.data.models.entity.MovieEntity

@Database(entities = [MovieEntity::class] , exportSchema = false , version = 1)
@TypeConverters(MoviesTypeConverter::class)
abstract class MovieDataBase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao
}