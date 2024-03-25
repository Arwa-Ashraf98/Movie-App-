package com.example.arwa.movieapp.domain.models

import com.example.arwa.movieapp.data.models.dto.Genre

data class DomainMovie(
    val genre : List<Genre> = emptyList(),
    val title: String ="",
    val runtime: Int = 0,
    val overview: String ="",
    val popularity: Double =0.0,
    val budget: Int =0 ,
    val posterPath : String = ""

)
