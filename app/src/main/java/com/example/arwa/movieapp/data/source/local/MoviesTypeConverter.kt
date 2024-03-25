package com.example.arwa.movieapp.data.source.local

import androidx.room.TypeConverter
import com.example.arwa.movieapp.data.models.dto.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class MoviesTypeConverter {

    @TypeConverter
    fun fromMoviesListToString(list: List<Result>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToMoviesList(movies: String): List<Result> {
        return try {
            val list = object : TypeToken<List<Result>>() {}.type
            return Gson().fromJson(movies, list)
        } catch (exception: IOException) {
            listOf()
        }
    }
}