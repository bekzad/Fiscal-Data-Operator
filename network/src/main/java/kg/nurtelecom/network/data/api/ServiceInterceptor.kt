package kg.nurtelecom.network.data.api

import android.util.Log
import kg.nurtelecom.storage.sharedpref.AppPreferences
import okhttp3.Interceptor
import okhttp3.Response

class ServiceInterceptor(private val appPrefs: AppPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        Log.d(appPrefs.token, "TOKEN")
        if (appPrefs.token.isNotEmpty()) {
            val finalToken = "Bearer ${appPrefs.token}"
            requestBuilder.addHeader("Authorization", finalToken)
        }
        val initialResponse = chain.proceed(requestBuilder.build())
        when (initialResponse.code) {
            401 -> {
                requestBuilder.addHeader("Authorization", "Basic ZGV2OkZndkRlNHZkITM=")
                return chain.proceed(requestBuilder.build())
            }
            else -> return initialResponse
        }
    }
}