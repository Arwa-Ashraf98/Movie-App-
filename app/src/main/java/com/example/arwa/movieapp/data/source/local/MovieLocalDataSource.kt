package com.example.arwa.movieapp.data.source.local

import com.example.arwa.movieapp.data.models.entity.MovieEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class
MovieLocalDataSource @Inject constructor(
    private val movieDao: MovieDao,
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher
) : IMovieLocalDataSource {

    override fun getMovies(): Flow<MovieEntity> {
        return flow<MovieEntity> {
                movieDao.getMovies()
        }.flowOn(ioDispatcher)

    }

    override suspend fun insertMovie(movieEntity: MovieEntity) {
        return withContext(ioDispatcher) {
            movieDao.insertMovies(movieEntity)
        }
    }
}