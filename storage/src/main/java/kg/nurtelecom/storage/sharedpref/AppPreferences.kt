package kg.nurtelecom.storage.sharedpref

import android.content.Context
import com.teamx.storage.base.BasePreferences
import com.teamx.storage.base.PreferenceDelegate

class AppPreferences(context: Context) : BasePreferences(context) {

    override val prefFileName: String
        get() = "kg.nurtelecom.storage.appPref"

    var token: String by PreferenceDelegate(sharedPreference, Keys.ACCESS_TOKEN, "")
    var refreshToken: String by PreferenceDelegate(sharedPreference, Keys.REFRESH_TOKEN, "")

    object Keys {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
    }
}