package kg.nurtelecom.ofd.ui.spalsh

import android.content.Intent
import kg.nurtelecom.auth.ui.AuthActivity
import kg.nurtelecom.core.activity.CoreActivity
import kg.nurtelecom.ofd.databinding.ActivitySplashBinding
import kg.nurtelecom.ofd.ui.main.MainActivity

class SplashActivity : CoreActivity<ActivitySplashBinding, SplashVM>(SplashVM::class) {
    override fun getBinding(): ActivitySplashBinding =
        ActivitySplashBinding.inflate(layoutInflater)

    override fun setupViews() {
        super.setupViews()
        startMainActivity()
    }

    private fun startMainActivity() {
        if (vm.isSign()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, AuthActivity::class.java)
            startActivityForResult(intent, AuthActivity.AUTH_RESULT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            AuthActivity.AUTH_RESULT -> {
                startMainActivity()
            }
        }
    }
}
