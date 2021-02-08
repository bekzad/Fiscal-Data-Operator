package kg.nurtelecom.changepassword.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kg.nurtelecom.core.activity.CoreActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.extension.startActivity
import kg.nurtelecom.user.R
import kg.nurtelecom.user.R.*
import kg.nurtelecom.user.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : CoreActivity<ActivityChangePasswordBinding,ChangePasswordViewModel>(ChangePasswordViewModel::class) {

    override fun setupViews() {
        setSupportActionBar(vb.toolbar)
        setToolbarTitle("Смена пароля")
        vb.toolbar.setNavigationIcon(R.drawable.ic_baseline_close_24)
        vb.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        replaceFragment<CurrentPasswordFragment>(R.id.container_main, false)
    }

    override fun getBinding(): ActivityChangePasswordBinding {
        return ActivityChangePasswordBinding.inflate(layoutInflater)
    }

    companion object {
        fun start(context: Context?) {
            context?.startActivity<ChangePasswordActivity>()
        }
    }
}