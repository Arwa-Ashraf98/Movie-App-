package com.example.arwa.movieapp.core.di

import com.example.arwa.movieapp.data.source.local.IMovieLocalDataSource
import com.example.arwa.movieapp.data.source.local.MovieLocalDataSource
import com.example.arwa.movieapp.data.source.remote.IMovieRemoteDataSource
import com.example.arwa.movieapp.data.source.remote.MovieRemoteDataSource
import com.example.arwa.movieapp.data.source.remote.MovieServices
import com.example.arwa.movieapp.domain.repo.IMovieRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {

    @Binds
    abstract fun bindMovieRemoteDataSource(movieRemoteDataSource: MovieRemoteDataSource): IMovieRemoteDataSource

    @Binds
    abstract fun bindMovieLocalDataSource(movieLocalDataSource: MovieLocalDataSource): IMovieLocalDataSource
}