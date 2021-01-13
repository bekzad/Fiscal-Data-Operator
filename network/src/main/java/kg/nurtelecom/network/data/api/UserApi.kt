package kg.nurtelecom.network.data.api

import kg.nurtelecom.data.UserDetail
import kg.nurtelecom.data.UserUpdateResult
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApi {

    @POST("update-profile")
    suspend fun updateUserProfile(
        @Header("Authorization") token: String,
        @Body userDetail: UserDetail)

    : UserUpdateResult
}
