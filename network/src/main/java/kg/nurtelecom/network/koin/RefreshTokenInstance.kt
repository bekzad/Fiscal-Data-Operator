package kg.nurtelecom.network.koin

import kg.nurtelecom.network.BuildConfig
import kg.nurtelecom.network.UnsafeOkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RefreshTokenInstance {
    private val client = UnsafeOkHttpClient.getUnsafeOkHttpClient().apply {
        if (BuildConfig.DEBUG)
            addInterceptor(okhttp3.logging.HttpLoggingInterceptor().setLevel(okhttp3.logging.HttpLoggingInterceptor.Level.BODY))
    }.build()

    private val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    fun <T> serviceBuilder(service: Class<T>): T {
        return retrofit.create(service)
    }
}