package kg.nurtelecom.storage.sharedpref

import android.content.Context
import kg.nurtelecom.storage.sharedpref.base.BasePreferences
import kg.nurtelecom.storage.sharedpref.base.PreferenceDelegate


class AppPreferences(context: Context) : BasePreferences(context) {

    override val prefFileName: String
        get() = "kg.nurtelecom.storage.appPref"

    var token: String by PreferenceDelegate(sharedPreference, Keys.ACCESS_TOKEN, "")
    var refreshToken: String by PreferenceDelegate(sharedPreference, Keys.REFRESH_TOKEN, "")
    var fiscalRegime: Boolean by PreferenceDelegate(sharedPreference, Keys.FISCAL_REGIME, false)

    object Keys {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
        const val FISCAL_REGIME = "FISCAL_REGIME"
    }
}