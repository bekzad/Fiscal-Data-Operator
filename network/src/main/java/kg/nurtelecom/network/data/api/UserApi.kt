package kg.nurtelecom.network.data.api

import kg.nurtelecom.data.*
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface UserApi {

    @POST("update-profile")
    suspend fun updateUserProfile(
        @Header("Authorization") token: String,
        @Body userDetail: UserDetail)

    : UserUpdateResult

    @POST("change-password")
    suspend fun changeUserPassword(
        @Header("Authorization") token: String,
        @Body changePasswordBody: ChangePasswordBody
    ) : ChangePasswordResult
}
