package kg.nurtelecom.auth.repository

import kg.nurtelecom.data.AccessToken
import kg.nurtelecom.network.data.api.AuthorizationApi
import kg.nurtelecom.storage.roomdatabase.DataDao
import kg.nurtelecom.storage.sharedpref.AppPreferences

class AuthRepository(
    private val authApi: AuthorizationApi,
    private val appPrefs: AppPreferences,
    private val dataDao: DataDao
) {
    suspend fun fetchAccessToken(login: String, password: String, gsrKey: String, isFiscalRegime: Boolean) : AccessToken {
        val response = authApi.fetchAccessToken(login, password, gsrKey)
        saveToken(response.access_token)
        saveRefreshToken(response.refresh_token)
        saveFiscalRegime(isFiscalRegime)
        fetchSecureKey(authApi.openSession("Bearer ${appPrefs.token}", appPrefs.token).result)
        return response
    }

    private fun saveFiscalRegime(fiscalRegime: Boolean) {
        appPrefs.fiscalRegime = fiscalRegime
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
    }

    private fun fetchSecureKey(secureKey: String) {
        appPrefs.secureKey = secureKey
    }
}
