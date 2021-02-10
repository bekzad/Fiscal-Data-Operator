package kg.nurtelecom.network.interceptors

import android.util.Log
import kg.nurtelecom.storage.sharedpref.AppPreferences
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody


class DecryptInterceptor(appPrefs: AppPreferences) : Interceptor {

    private val encryption = Encryption(appPrefs)

    override fun intercept(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.request())
        var contentType = response.header("Content-Type")
        if (contentType.isNullOrEmpty()) contentType = "application/json"
        var responseString = "No response body/error body from api"
        if (response.body != null) {
            responseString = response.body!!.string()
        }

        var decryptedString = responseString
        if (response.code != 401) {
            decryptedString = encryption.decrypt(responseString)
        }
        Log.e("DecryptInterceptor", decryptedString)

        val newResponse = response.newBuilder()
        return newResponse
                .body(decryptedString.toResponseBody(contentType.toMediaTypeOrNull()))
                .build()
    }
}