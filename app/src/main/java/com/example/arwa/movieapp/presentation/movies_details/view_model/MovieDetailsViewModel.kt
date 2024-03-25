package com.example.arwa.movieapp.presentation.movies_details.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arwa.movieapp.R
import com.example.arwa.movieapp.core.resource.ResourceResult
import com.example.arwa.movieapp.core.resource.ScreenUiState
import com.example.arwa.movieapp.domain.interactors.GetMovieDetailsUseCase
import com.example.arwa.movieapp.domain.models.DomainMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private var _movieFlow: MutableStateFlow<ScreenUiState<DomainMovie>> =
        MutableStateFlow(ScreenUiState.Loading)
    val movieFlow: StateFlow<ScreenUiState<DomainMovie>> = _movieFlow

    fun getMovie(movieId: Int) {
        viewModelScope.launch {
            _movieFlow.value = ScreenUiState.Loading
            when (val res = getMovieDetailsUseCase(movieId = movieId)) {
                is ResourceResult.SUCCESS -> {
                    _movieFlow.value = ScreenUiState.Success(res.data)
                }

                is ResourceResult.ERROR -> {
                    _movieFlow.value =
                        ScreenUiState.Error(res.error?.localizedMessage ?: "Something Went Wrong")
                }
            }

        }
    }

    fun checkPopularity(popularity: Int): Int {
        return if (popularity <= 500) {
            R.string.not_popular
        } else if (popularity in 501..1000) {
            R.string.known
        } else if (popularity in 1001..2000) {
            R.string.popular
        } else {
            R.string.very_popular
        }
    }
}