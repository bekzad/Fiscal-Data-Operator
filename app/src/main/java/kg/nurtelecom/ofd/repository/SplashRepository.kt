package kg.nurtelecom.ofd.repository

import kg.nurtelecom.storage.sharedpref.AppPreferences

class SplashRepository(private val appPref: AppPreferences) {
    fun isSign() = appPref.token.isNotEmpty()
}