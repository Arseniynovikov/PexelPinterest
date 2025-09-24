package com.example.pinterest.pexelApi

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named

class AuthInterceptor @Inject constructor(
    @Named("pexels_api_key") private val apiKey: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", apiKey)
            .build()
        return chain.proceed(request)
    }
}
