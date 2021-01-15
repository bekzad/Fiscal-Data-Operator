package kg.nurtelecom.changepassword.repository

import kg.nurtelecom.data.ChangePasswordBody
import kg.nurtelecom.network.data.api.UserApi
import kg.nurtelecom.storage.sharedpref.AppPreferences


class ChangePasswordRepository(
    private val userApi: UserApi,
    private val appPreferences: AppPreferences
) {
    suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ): Boolean {
        val result = userApi.changeUserPassword(
            "Bearer ${appPreferences.token}",
            ChangePasswordBody(currentPassword, newPassword, confirmPassword)
        )
        return result.result
    }
}