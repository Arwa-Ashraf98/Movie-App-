package com.example.arwa.movieapp.data.models.dto

data class ErrorResponse(
    val status_code: Int,
    val status_message: String,
    val success: Boolean
) : Throwable()