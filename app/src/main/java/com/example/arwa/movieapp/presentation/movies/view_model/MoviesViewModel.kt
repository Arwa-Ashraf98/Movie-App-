package com.example.arwa.movieapp.presentation.movies.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arwa.movieapp.core.resource.ResourceResult
import com.example.arwa.movieapp.core.resource.ScreenUiState
import com.example.arwa.movieapp.domain.interactors.GetMoviesUseCase
import com.example.arwa.movieapp.domain.models.DomainMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase) :
    ViewModel() {

    private var _moviesFlow: MutableStateFlow<ScreenUiState<List<DomainMovies>>> =
        MutableStateFlow(ScreenUiState.Loading)
    val movieFlow: StateFlow<ScreenUiState<List<DomainMovies>>> = _moviesFlow


    init {
        getMovies()
    }


    private fun getMovies() {
        viewModelScope.launch {
            getMoviesUseCase()
                .catch {
                    _moviesFlow.value = ScreenUiState.Error(it.localizedMessage ?: "Error Happen")
                }
                .collectLatest { res ->
                    when (res) {
                        is ResourceResult.SUCCESS -> {
                            _moviesFlow.value = ScreenUiState.Success(res.data)
                        }

                        is ResourceResult.ERROR -> {
                            _moviesFlow.value = ScreenUiState.Error(
                                res.error?.localizedMessage ?: "Something went wrong"
                            )
                        }
                    }
                }
        }
    }
}