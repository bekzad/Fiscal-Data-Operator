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
    var secureKey: String by PreferenceDelegate(sharedPreference, Keys.SECURE_KEY, "")
    var currentDate: String by PreferenceDelegate(sharedPreference, Keys.CURRENT_DATE, "")
    var isFiscalDialogShow: Boolean by PreferenceDelegate(sharedPreference, Keys.FISCAL_DIALOG_STATE, false)

    object Keys {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
        const val FISCAL_REGIME = "FISCAL_REGIME"
        const val SECURE_KEY = "SECURE_KEY"
        const val CURRENT_DATE = "CURRENT_DATE"
        const val FISCAL_DIALOG_STATE = "FISCAL_DIALOG_STATE"
    }
}