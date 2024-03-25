package com.example.arwa.movieapp.data.repo

import com.example.arwa.movieapp.core.resource.ResourceResult
import com.example.arwa.movieapp.data.mapper.mapToEntity
import com.example.arwa.movieapp.data.mapper.toDomainMovie
import com.example.arwa.movieapp.data.models.entity.CachingConfig
import com.example.arwa.movieapp.data.source.local.IMovieLocalDataSource
import com.example.arwa.movieapp.data.source.remote.IMovieRemoteDataSource
import com.example.arwa.movieapp.domain.models.DomainMovie
import com.example.arwa.movieapp.domain.models.DomainMovies
import com.example.arwa.movieapp.domain.repo.IMovieRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class MovieRepo @Inject constructor(
    private val movieLocalDataSource: IMovieLocalDataSource,
    private val movieRemoteDataSource: IMovieRemoteDataSource,
    @Named("defaultDispatcher") private val defaultDispatcher: CoroutineDispatcher
) : IMovieRepo {


    override fun getMovies(): Flow<ResourceResult<List<DomainMovies>>> {
        return flow {
            val localResult = movieLocalDataSource.getMovies().firstOrNull()
            if (isDataExpired(localResult?.cachingConfig) || localResult?.movies?.isEmpty() == true) {
                try {
                    when (val remoteResult = movieRemoteDataSource.getMovies()) {
                        is ResourceResult.SUCCESS -> {
                            val movieEntity = remoteResult.data.mapToEntity()
                            movieLocalDataSource.insertMovie(movieEntity)
                            emit(ResourceResult.SUCCESS(remoteResult.data.map { it.toDomainMovie() }))
                        }

                        is ResourceResult.ERROR -> {
                            emit(ResourceResult.ERROR(remoteResult.error))
                        }
                    }
                } catch (e: Exception) {
                    emit(ResourceResult.ERROR(e))
                }
            } else {
                val movies = localResult?.movies?.map { it.toDomainMovie() }
                emit(ResourceResult.SUCCESS(movies ?: emptyList()))
            }
        }.catch { ex ->
            emit(ResourceResult.ERROR(ex))
        }
            .flowOn(defaultDispatcher)

    }

    override suspend fun getMovieDetails(movieId: Int): ResourceResult<DomainMovie> {
        return withContext(defaultDispatcher) {
            when (val res = movieRemoteDataSource.getMovieDetails(movieId = movieId)) {
                is ResourceResult.SUCCESS -> {
                    val domainMovie = res.data?.toDomainMovie() ?: DomainMovie()
                    ResourceResult.SUCCESS(domainMovie)
                }

                is ResourceResult.ERROR -> {
                    ResourceResult.ERROR(res.error)
                }
            }
        }
    }

    private fun isDataExpired(cachingConfig: CachingConfig?): Boolean {
        if (cachingConfig?.expirationHours == null)
            return true
        if (checkIfHoursExpired(cachingConfig))
            return true
        return false
    }

    private fun checkIfHoursExpired(cachingConfig: CachingConfig?): Boolean {
        if (cachingConfig?.expirationHours == null)
            return true
        val expirationInMillieSeconds =
            (cachingConfig.lastUpdatedDate!!.toLong() + (cachingConfig.expirationHours * 60 * 60 * 1000))
        return System.currentTimeMillis() > expirationInMillieSeconds
    }

}