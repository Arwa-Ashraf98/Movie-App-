package com.example.arwa.movieapp.core.di

import com.example.arwa.movieapp.domain.interactors.GetMovieDetailsUseCase
import com.example.arwa.movieapp.domain.interactors.GetMoviesUseCase
import com.example.arwa.movieapp.domain.repo.IMovieRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetMoviesUseCase(
        movieRepo: IMovieRepo,
        @Named("defaultDispatcher") defaultDispatcher: CoroutineDispatcher
    ): GetMoviesUseCase  = GetMoviesUseCase(movieRepo)


    @Provides
    fun provideGetMovieDetailsUseCase(
        movieRepo: IMovieRepo,
        @Named("defaultDispatcher") defaultDispatcher: CoroutineDispatcher
    ): GetMovieDetailsUseCase  = GetMovieDetailsUseCase(movieRepo)

}