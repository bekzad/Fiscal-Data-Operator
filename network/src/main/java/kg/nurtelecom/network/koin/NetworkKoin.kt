package kg.nurtelecom.network.koin

import kg.nurtelecom.network.BuildConfig
import kg.nurtelecom.network.UnsafeOkHttpClient
import kg.nurtelecom.network.data.api.AuthorizationApi
import kg.nurtelecom.network.data.api.HistoryApi
import kg.nurtelecom.network.data.api.ProductApi
import kg.nurtelecom.network.data.api.ReportsApi
import kg.nurtelecom.network.data.api.UserApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkKoin = module {

    single { provideOkHttp() }
    single { provideRetrofit(get()) }
    single { get<Retrofit>().create(AuthorizationApi::class.java) }
    single { get<Retrofit>().create(HistoryApi::class.java) }
    single { get<Retrofit>().create(UserApi::class.java) }
    single { get<Retrofit>().create(ProductApi::class.java) }
    single { get<Retrofit>().create(ReportsApi::class.java) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideOkHttp(): OkHttpClient {
    val builder = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    if (BuildConfig.DEBUG)
        builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    return builder.build()
}
