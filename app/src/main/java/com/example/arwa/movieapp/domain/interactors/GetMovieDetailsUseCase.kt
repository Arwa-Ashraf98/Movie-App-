package com.example.arwa.movieapp.domain.interactors

import com.example.arwa.movieapp.core.resource.ResourceResult
import com.example.arwa.movieapp.domain.models.DomainMovie
import com.example.arwa.movieapp.domain.repo.IMovieRepo
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val movieRepo: IMovieRepo) {

    suspend operator fun invoke(movieId : Int) : ResourceResult<DomainMovie> {
        return movieRepo.getMovieDetails(movieId)
    }
}