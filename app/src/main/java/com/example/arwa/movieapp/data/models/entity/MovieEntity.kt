package com.example.arwa.movieapp.data.models.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.arwa.movieapp.core.utils.Constants
import com.example.arwa.movieapp.data.models.dto.Result

@Entity(Constants.MOVIE_TABLE_NAME )
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @TypeConverters
    val movies: List<Result>,
    @Embedded
    val cachingConfig: CachingConfig
)
