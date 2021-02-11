package kg.nurtelecom.auth.ui

import android.content.Context
import kg.nurtelecom.auth.databinding.AuthActivityBinding
import kg.nurtelecom.core.activity.CoreActivity
import kg.nurtelecom.core.extension.enable
import kg.nurtelecom.core.extension.startActivity

class  AuthActivity : CoreActivity<AuthActivityBinding, AuthViewModel>(AuthViewModel::class) {

    private fun login() {
        val (login, password, gsrKey) = editTextHandler()
        vm.login(login, password, gsrKey, isFiscalRegime())
    }

    private fun editTextHandler(): Array<String> {
        val login = vb.etLogin.getText().trim()
        val password = vb.etPassword.getText().trim()
        val gsrKey = vb.etGsrKey.getText().trim()
        return arrayOf(login, password, gsrKey)
    }

    private fun isFiscalRegime(): Boolean {
        return  vb.switchFiscalRegime.isChecked
    }

    override fun setupViews() {
        vb.btnLogin.enable(false)

        vb.etLogin.setTextChangedListener {
            val (login, password, gsrKey) = editTextHandler()
            vb.btnLogin.enable(login.isNotEmpty() && password.isNotEmpty() && gsrKey.isNotEmpty())
        }

        vb.etPassword.setTextChangedListener {
            val (login, password, gsrKey) = editTextHandler()
            vb.btnLogin.enable(login.isNotEmpty() && password.isNotEmpty() && gsrKey.isNotEmpty())
        }

        vb.etGsrKey.setTextChangedListener {
            val (login, password, gsrKey) = editTextHandler()
            vb.btnLogin.enable(login.isNotEmpty() && password.isNotEmpty() && gsrKey.isNotEmpty())
        }

        vb.btnLogin.setOnClickListener { login() }
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        observeAuthorization()
    }

    private fun observeAuthorization() {
        vm.event.observe(this, {
            when (it) {
                is AuthUser -> {
                    setResult(AUTH_RESULT)
                    finish()
                }
            }
        })
    }

    override fun getBinding() = AuthActivityBinding.inflate(layoutInflater)

    override fun onBackPressed() {
        return
    }

    companion object {
        const val AUTH_RESULT = 1
        fun start(context: Context?) {
            context?.startActivity<AuthActivity>()
        }
    }
}