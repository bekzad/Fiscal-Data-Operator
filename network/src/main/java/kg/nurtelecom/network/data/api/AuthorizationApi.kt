package kg.nurtelecom.network.data.api

import kg.nurtelecom.data.AccessToken
import kg.nurtelecom.data.LogoutResult
import kg.nurtelecom.data.UserResult
import retrofit2.Response
import retrofit2.http.*

interface AuthorizationApi {
    @Headers("Authorization: Basic ZGV2OkZndkRlNHZkITM=")
    @POST("oauth/token?grant_type=password&scope=read")
    suspend fun fetchAccessToken(@Query("username") username: String,
                                 @Query("password") password: String,
                                 @Query("gns_key") gnsKey: String): AccessToken

    @GET("me")
    suspend fun fetchUserData(
        @Header("Authorization") token: String
    ): UserResult

    @Headers("Authorization: Basic ZGV2OkZndkRlNHZkITM={token}")
    @POST("invalidate")
    suspend fun logout(@Header("token") token: String): LogoutResult
}
