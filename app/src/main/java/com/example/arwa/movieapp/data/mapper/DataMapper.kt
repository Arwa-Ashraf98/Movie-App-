package com.example.arwa.movieapp.data.mapper

import com.example.arwa.movieapp.data.models.dto.MovieResponse
import com.example.arwa.movieapp.data.models.dto.Result
import com.example.arwa.movieapp.data.models.entity.CachingConfig
import com.example.arwa.movieapp.data.models.entity.MovieEntity
import com.example.arwa.movieapp.domain.models.DomainMovie
import com.example.arwa.movieapp.domain.models.DomainMovies


fun Result.toDomainMovie(): DomainMovies {
    return DomainMovies(
        posterPath = this.poster_path,
        originalTitle = this.original_title,
        title = this.title,
        id = this.id,
        date = this.release_date,
        adult = this.adult
    )
}

fun MovieResponse.toDomainMovie(): DomainMovie {
    return DomainMovie(
        genre = this.genres,
        title = this.title,
        runtime = this.runtime,
        overview = this.overview,
        popularity = this.popularity,
        budget = this.budget,
        posterPath = this.poster_path
    )
}

fun List<Result>.mapToEntity(): MovieEntity {
    val cachingConfig = CachingConfig(
        lastUpdatedDate = System.currentTimeMillis(),
        expirationHours = 4
    )
    return MovieEntity(
        id = 0,
        movies = this,
        cachingConfig = cachingConfig

    )
}

