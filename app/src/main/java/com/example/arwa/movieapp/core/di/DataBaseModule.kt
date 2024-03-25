package com.example.arwa.movieapp.core.di

import android.content.Context
import androidx.room.Room
import com.example.arwa.movieapp.core.utils.Constants
import com.example.arwa.movieapp.data.source.local.MovieDao
import com.example.arwa.movieapp.data.source.local.MovieDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context): MovieDataBase =
        Room.databaseBuilder(context, MovieDataBase::class.java, Constants.MOVIE_DATA_BASE_NAME)
            .build()

    @Provides
    @Singleton
    fun provideMovieDao(movieDataBase: MovieDataBase): MovieDao = movieDataBase.getMovieDao()
}