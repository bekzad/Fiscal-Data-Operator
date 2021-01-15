package kg.nurtelecom.changepassword.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kg.nurtelecom.core.activity.CoreActivity
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.user.R
import kg.nurtelecom.user.R.*
import kg.nurtelecom.user.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : CoreActivity<ActivityChangePasswordBinding,ChangePasswordViewModel>(ChangePasswordViewModel::class) {

    override fun setupViews() {
        super.setupViews()
        setSupportActionBar(vb.toolbar)
        setToolbarTitle("Смена пароля")
        vb.toolbar.setNavigationIcon(R.drawable.ic_baseline_close_24)
        vb.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_change_password)
        showChangePasswordFragment()
    }

    override fun getBinding(): ActivityChangePasswordBinding {
        return ActivityChangePasswordBinding.inflate(layoutInflater)
    }

    private fun showChangePasswordFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.container_main,CurrentPasswordFragment.newInstance())
            .commit()
    }

}