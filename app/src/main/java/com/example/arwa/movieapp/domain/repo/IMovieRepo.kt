package com.example.arwa.movieapp.domain.repo

import com.example.arwa.movieapp.core.resource.ResourceResult
import com.example.arwa.movieapp.data.models.dto.MovieResponse
import com.example.arwa.movieapp.domain.models.DomainMovie
import com.example.arwa.movieapp.domain.models.DomainMovies
import kotlinx.coroutines.flow.Flow

interface IMovieRepo {

    fun getMovies() : Flow<ResourceResult<List<DomainMovies>>>

    suspend fun getMovieDetails(movieId : Int) : ResourceResult<DomainMovie>
}