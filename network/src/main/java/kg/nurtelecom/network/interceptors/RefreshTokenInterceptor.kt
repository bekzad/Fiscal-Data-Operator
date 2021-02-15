package kg.nurtelecom.network.interceptors

import kg.nurtelecom.network.data.api.RefreshTokenApi
import kg.nurtelecom.network.koin.RefreshTokenInstance
import kg.nurtelecom.storage.sharedpref.AppPreferences
import okhttp3.Interceptor
import okhttp3.Request


class RefreshTokenInterceptor(private val appPrefs: AppPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

        var request = chain.request()
        // Make a request and save the response here
        val response = chain.proceed(request)

        // If the response code is 401 then, fetch new token and make a new request with new token
        if (response.code == 401) {
            return makeTokenRefreshCall(request, chain)
        }

        // If the response is not 401 then return the normal response
        return response
    }

    private fun makeTokenRefreshCall(req: Request, chain: Interceptor.Chain): okhttp3.Response {
        /* fetch refresh token and save it to appPrefs */
        fetchRefreshToken()

        /* make a new request which is same as the original one, except that its headers now contain a refreshed token */
        val newRequest: Request = if (!req.header("session_uuid").isNullOrEmpty()) {
            req.newBuilder()
                    .header("Authorization", "Bearer ${appPrefs.token}")
                    .header("session_uuid", appPrefs.token)
                    .build()
        } else {
            req.newBuilder()
                    .header("Authorization", "Bearer ${appPrefs.token}")
                    .build()
        }

        val newResponse = chain.proceed(newRequest)
        return newResponse
    }

    private fun fetchRefreshToken() {

        val request = RefreshTokenInstance.serviceBuilder(RefreshTokenApi::class.java)

        // Synchronous call to api to get the new access and refresh token
        val callAccessToken = request.fetchRefreshToken(appPrefs.refreshToken)

        val accessToken = callAccessToken.execute().body()

            // Save to apprefs new access and refresh token
            appPrefs.token = accessToken?.access_token ?: ""
            appPrefs.refreshToken = accessToken?.refresh_token ?: ""

            // Synchronous call to api with new access token to get the secret key
            val callSecureKey = request.openSession("Bearer ${appPrefs.token}", appPrefs.token)
            val secureKey = callSecureKey.execute().body()

            // Save the secret key to
            appPrefs.secureKey = secureKey?.result ?: ""
        }
}