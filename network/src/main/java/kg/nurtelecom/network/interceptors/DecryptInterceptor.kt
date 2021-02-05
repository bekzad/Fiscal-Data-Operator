package kg.nurtelecom.network.interceptors

import kg.nurtelecom.storage.sharedpref.AppPreferences
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody


class DecryptInterceptor(private val appPrefs: AppPreferences) : Interceptor {

    internal val encryption = Encryption(appPrefs)

    override fun intercept(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.request())
        val newResponse = response.newBuilder()
        var contentType = response.header("Content-Type")
        if (contentType.isNullOrEmpty()) contentType = "application/json"
        val responseString = response.body!!.string()

        var decryptedString = ""
        decryptedString = encryption.decrypt(responseString)

        newResponse.body(decryptedString.toResponseBody(contentType.toMediaTypeOrNull()))
        return newResponse.build()
    }
}