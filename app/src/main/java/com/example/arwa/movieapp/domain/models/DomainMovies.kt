package com.example.arwa.movieapp.domain.models

data class DomainMovies(
    val posterPath: String,
    val originalTitle: String,
    val title: String,
    val id: Int,
    val date: String,
    val adult: Boolean
)
