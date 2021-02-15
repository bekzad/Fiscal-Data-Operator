package kg.nurtelecom.network.interceptors

import kg.nurtelecom.storage.sharedpref.AppPreferences
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.Buffer

class EncryptInterceptor(appPrefs: AppPreferences) : Interceptor {

    private val encryption = Encryption(appPrefs)

    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        val oldBody = request.body
        val buffer = Buffer()
        oldBody!!.writeTo(buffer)
        val strOldBody: String = buffer.readUtf8()

        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val strNewBody: String = encryption.encrypt(strOldBody)
        val body: RequestBody = strNewBody.toRequestBody(mediaType)

        request = request.newBuilder()
            .header("Content-Type", body.contentType().toString())
            .header("Content-Length", body.contentLength().toString())
            .method(request.method, body).build()

        return chain.proceed(request)
    }
}