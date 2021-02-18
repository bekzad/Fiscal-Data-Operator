package kg.nurtelecom.network.koin

import kg.nurtelecom.network.BuildConfig
import kg.nurtelecom.network.UnsafeOkHttpClient
import kg.nurtelecom.network.data.api.*
import kg.nurtelecom.network.interceptors.DecryptInterceptor
import kg.nurtelecom.network.interceptors.EncryptInterceptor
import kg.nurtelecom.network.interceptors.RefreshTokenInterceptor
import kg.nurtelecom.storage.sharedpref.AppPreferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

val networkKoin = module {

    single { provideOkHttp(get()) }
    single { provideRetrofit(get()) }
    single(named("encryptedOkHttp")) { provideOkHttpEncrypted(get()) }
    single(named("encryptedRetrofit")) { provideEncryptedRetrofit(get(named("encryptedOkHttp"))) }
    single { get<Retrofit>().create(AuthorizationApi::class.java) }
    single { get<Retrofit>().create(HistoryApi::class.java) }
    single { get<Retrofit>().create(UserApi::class.java) }
    single { get<Retrofit>().create(ReportsApi::class.java) }
    single { get<Retrofit>().create(ProductApi::class.java) }
    single { get<Retrofit>(named("encryptedRetrofit")).create(SellApi::class.java) }
    single { get<Retrofit>(named("encryptedRetrofit")).create(ReceiptInOutApi::class.java) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideOkHttp(appPrefs: AppPreferences): OkHttpClient {
    val builder = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    builder.addInterceptor(RefreshTokenInterceptor(appPrefs))
    if (BuildConfig.DEBUG)
        builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    return builder.build()
}

fun provideEncryptedRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
}

fun provideOkHttpEncrypted(appPrefs: AppPreferences): OkHttpClient {
    val builder = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    if (BuildConfig.DEBUG)
        builder.addInterceptor(HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY))
    builder
            .addInterceptor((RefreshTokenInterceptor(appPrefs)))
            .addInterceptor(EncryptInterceptor(appPrefs))
            .addInterceptor(DecryptInterceptor(appPrefs))
    return builder.build()
}