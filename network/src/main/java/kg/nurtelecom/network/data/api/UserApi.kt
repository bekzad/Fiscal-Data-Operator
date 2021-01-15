package kg.nurtelecom.network.data.api

import kg.nurtelecom.data.UserDetail
import kg.nurtelecom.data.UserUpdateResult
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.*
import kg.nurtelecom.data.ChangePasswordBody
import kg.nurtelecom.data.UserDetailModel


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
