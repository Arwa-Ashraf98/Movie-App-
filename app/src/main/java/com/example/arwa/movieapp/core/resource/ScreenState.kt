package com.example.arwa.movieapp.core.resource

import androidx.annotation.StringRes
import java.lang.Error

sealed class ScreenUiState<out T> {
    data class Success<T>(val data: T) : ScreenUiState<T>()
    data class Error(val error: String) : ScreenUiState<Nothing>()
    object Loading : ScreenUiState<Nothing>()
}
