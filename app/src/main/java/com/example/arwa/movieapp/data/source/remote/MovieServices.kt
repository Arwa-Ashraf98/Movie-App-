package com.example.arwa.movieapp.data.source.remote

import com.example.arwa.movieapp.data.models.dto.MovieResponse
import com.example.arwa.movieapp.data.models.dto.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieServices {

    companion object {
        private const val MOVIES_RELATIVE_LINK = "discover/movie"
        private const val MOVIE_DETAILS_RELATIVE_LINK = "movie/{movie_id}"
    }

    @GET(MOVIES_RELATIVE_LINK)
    suspend fun getMovies() : Response<MoviesResponse>

    @GET(MOVIE_DETAILS_RELATIVE_LINK)
    suspend fun getMovieDetails(@Path("movie_id") movieId : Int) : Response<MovieResponse>
}