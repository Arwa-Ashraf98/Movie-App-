package com.example.arwa.movieapp.domain.interactors

import com.example.arwa.movieapp.core.resource.ResourceResult
import com.example.arwa.movieapp.domain.models.DomainMovies
import com.example.arwa.movieapp.domain.repo.IMovieRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val movieRepo: IMovieRepo) {

    suspend operator fun invoke() : Flow<ResourceResult<List<DomainMovies>>> {
        return movieRepo.getMovies()
    }
}