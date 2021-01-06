package kg.nurtelecom.ofd.application

import android.app.Application
import com.teamx.storage.storageKoin.storageKoin
import kg.nurtelecom.auth.koin.authKoin
import kg.nurtelecom.network.koin.networkKoin
import kg.nurtelecom.ofd.koin.appKoin
import kg.nurtelecom.sell.koin.sellKoin
import kg.nurtelecom.user.koin.userKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class OfdApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@OfdApplication)
            modules(appKoin, networkKoin, storageKoin, authKoin, sellKoin, userKoin)
        }
    }
}
