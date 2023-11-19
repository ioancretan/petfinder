package com.ioan.animals.main


import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val apiService: RefreshTokenClient) : Interceptor {
    private val sessionManager = SessionManager()

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val accessToken = sessionManager.accessToken

        if ((accessToken != null && sessionManager.isAccessTokenExpired() )|| accessToken == null) {
            val response = apiService.refreshAccessToken(RefreshTokenBody()).blockingGet()
            val refreshedToken = response.accessToken
            sessionManager.updateAccessToken(response.accessToken, response.expiresIn)
            response.accessToken


            if (refreshedToken != null) {
                // Create a new request with the refreshed access token
                val newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $refreshedToken")
                    .build()

                // Retry the request with the new access token
                return chain.proceed(newRequest)
            }
        }

        // Add the access token to the request header
        val authorizedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()

        return chain.proceed(authorizedRequest)
    }
}