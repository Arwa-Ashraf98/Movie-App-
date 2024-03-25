package com.example.arwa.movieapp.data.source.remote

import com.example.arwa.movieapp.core.resource.ResourceResult
import com.example.arwa.movieapp.data.models.dto.MovieResponse
import com.example.arwa.movieapp.data.models.dto.Result

interface IMovieRemoteDataSource {

    suspend fun getMovies() : ResourceResult<List<Result>>

    suspend fun getMovieDetails(movieId : Int) : ResourceResult<MovieResponse?>
}