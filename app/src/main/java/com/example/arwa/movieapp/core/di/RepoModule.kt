package com.example.arwa.movieapp.core.di

import com.example.arwa.movieapp.data.repo.MovieRepo
import com.example.arwa.movieapp.domain.repo.IMovieRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepoModule {

    @Binds
    abstract fun bindMovieRepo(movieRepo: MovieRepo): IMovieRepo


}