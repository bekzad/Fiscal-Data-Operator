package kg.nurtelecom.sell.repository

import kg.nurtelecom.storage.sharedpref.AppPreferences

class SellRepository(private val appPrefs: AppPreferences) {

    fun fetchRegime(): Boolean = appPrefs.fiscalRegime
}