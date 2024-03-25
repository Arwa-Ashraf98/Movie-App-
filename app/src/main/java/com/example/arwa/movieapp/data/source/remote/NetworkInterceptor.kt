package com.example.arwa.movieapp.data.source.remote

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named

class NetworkInterceptor @Inject constructor(@Named("apiKey") private val apiKey: String) :
    Interceptor {

    companion object {
        private const val CONTENT_TYPE = "Content-Type"
        private const val APPLICATION_JSON = "application/json"
        private const val ACCEPT = "Accept"
        private const val API_KEY = "api_key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url
        val modifiedUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(API_KEY, apiKey)
            .build()

        val requestBuilder = originalRequest.newBuilder()
            .url(modifiedUrl)
            .addHeader(CONTENT_TYPE, APPLICATION_JSON)
            .addHeader(ACCEPT, APPLICATION_JSON)

        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)

    }


}