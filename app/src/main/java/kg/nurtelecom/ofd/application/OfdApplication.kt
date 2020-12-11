package kg.nurtelecom.ofd.application

import android.app.Application
import kg.nurtelecom.ofd.koin.appKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class OfdApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@OfdApplication)
            modules(appKoin)
        }
    }
}