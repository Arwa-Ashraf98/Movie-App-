package com.example.arwa.movieapp.data.source.remote

import com.example.arwa.movieapp.core.resource.ResourceResult
import com.example.arwa.movieapp.data.models.dto.MovieResponse
import com.example.arwa.movieapp.data.models.dto.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class
MovieRemoteDataSource @Inject constructor(
    private val movieServices: MovieServices,
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher
) : IMovieRemoteDataSource {

    override suspend fun getMovies(): ResourceResult<List<Result>> {
        return withContext(ioDispatcher) {
            try {
                val response = movieServices.getMovies()
                if (response.isSuccessful) {
                    ResourceResult.SUCCESS(response.body()?.results ?: emptyList())
                } else {
                    ResourceResult.ERROR(Throwable(response.message()))
                }
            } catch (e: Exception) {
                ResourceResult.ERROR(e)
            }
        }
    }

    override suspend fun getMovieDetails(movieId: Int): ResourceResult<MovieResponse?> {
        return withContext(ioDispatcher) {
            try {
                val response = movieServices.getMovieDetails(movieId)
                if (response.isSuccessful) {
                    ResourceResult.SUCCESS(response.body())
                } else {
                    ResourceResult.ERROR(Throwable(response.message()))
                }
            } catch (e: Exception) {
                ResourceResult.ERROR(e)
            }
        }
    }
}