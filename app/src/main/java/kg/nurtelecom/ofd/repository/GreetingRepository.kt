package kg.nurtelecom.ofd.repository

import kg.nurtelecom.data.LogoutResult
import kg.nurtelecom.network.data.api.AuthorizationApi
import kg.nurtelecom.storage.sharedpref.AppPreferences

class GreetingRepository(private val network: AuthorizationApi, private val appPref: AppPreferences) {

    suspend fun logout(): LogoutResult {
        val result = network.logout(appPref.token)
        appPref.token = ""
        appPref.refreshToken = ""
        appPref.secureKey = ""
        return result
    }
}