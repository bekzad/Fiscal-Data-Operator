package kg.nurtelecom.network.data.api

import kg.nurtelecom.data.AccessToken
import kg.nurtelecom.data.session.OpenSession
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query


interface RefreshTokenApi {
    // Synchronous request api to get the refresh token
    @Headers("Authorization: Basic ZGV2OkZndkRlNHZkITM=")
    @POST("oauth/token?grant_type=refresh_token")
    fun fetchRefreshToken(
            @Query("refresh_token") oldRefreshToken: String
    ): Call<AccessToken>

    @POST("get-secure-key?invalidate_existing=true")
    fun openSession(
            @Header("Authorization") token: String,
            @Header("session_uuid") session_uuid: String
    ): Call<OpenSession>
}
