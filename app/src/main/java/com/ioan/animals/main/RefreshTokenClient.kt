package com.ioan.animals.main

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenClient {
    @POST("oauth2/token")
    fun refreshAccessToken(@Body refreshTokenBody: RefreshTokenBody): Single<TokenResponse>
}

data class TokenResponse(
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("expires_in")
    val expiresIn: Long,
    @SerializedName("access_token")
    val accessToken: String
)


//TODO this should go into key store - do not have time to finish... sorry
data class RefreshTokenBody(
    @SerializedName("grant_type")
    val grantType: String = "client_credentials",
    @SerializedName("client_id")
    val client_id: String = "1ezodHxC8TLLTb0i0ydXcWjGfST1S44BDQswCKIINjhnT6JJsF",
    @SerializedName("client_secret")
    val client_secret: String = "LoppIQ56Hco9fUYtvYCx3sLWhoRkR1hneMTa5Icl"
)