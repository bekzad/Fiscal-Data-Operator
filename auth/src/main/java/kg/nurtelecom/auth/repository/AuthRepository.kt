package kg.nurtelecom.auth.repository

import android.util.Log
import kg.nurtelecom.network.data.api.AuthorizationApi
import kg.nurtelecom.storage.roomDatabase.DataDao
import kg.nurtelecom.storage.sharedpref.AppPreferences

class AuthRepository(
    private val authApi: AuthorizationApi,
    private val appPrefs: AppPreferences,
    private val dataDao: DataDao
) : BaseRepository() {

    fun isSigning() = appPrefs.token.isNotEmpty()

    suspend fun fetchAccessToken(login: String, password: String, gsrKey: String) = safeApiCall {
        val response = authApi.fetchAccessToken(login, password, gsrKey)
        saveToken(response.access_token)
        saveRefreshToken(response.refresh_token)
        response
    }

    private fun saveToken(token: String) {
        appPrefs.token = token
    }

    private fun saveRefreshToken(refreshToken: String) {
        appPrefs.refreshToken = refreshToken
    }

    suspend fun fetchUserData() {
        val user = authApi.fetchUserData("Bearer ${appPrefs.token}")
        dataDao.insert(user.result.user.userDetail)
        Log.d("USER_DATA", dataDao.getAllData().toString())
    }
}