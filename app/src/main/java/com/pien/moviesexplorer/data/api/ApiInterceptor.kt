package com.pien.moviesexplorer.data.api

import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor(private val apiKey: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .url(request.url().newBuilder().addQueryParameter("api_key", apiKey).build())
            .addHeader("accept", "application/json")
            .build()
        return chain.proceed(newRequest)
    }
}