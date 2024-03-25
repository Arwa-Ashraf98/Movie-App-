package com.example.arwa.movieapp.data.source.local

import com.example.arwa.movieapp.data.models.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface IMovieLocalDataSource {

    fun getMovies() : Flow<MovieEntity>

    suspend fun insertMovie(movieEntity: MovieEntity)
}