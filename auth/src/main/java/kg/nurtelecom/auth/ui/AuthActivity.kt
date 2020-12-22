package kg.nurtelecom.auth.ui

import androidx.lifecycle.Observer
import kg.nurtelecom.auth.databinding.AuthActivityBinding
import kg.nurtelecom.core.activity.CoreActivity
import kg.nurtelecom.core.extension.getTrimmedText
import kg.nurtelecom.core.extension.enable
import kg.nurtelecom.core.extension.visible

class AuthActivity : CoreActivity<AuthActivityBinding, AuthViewModel>(AuthViewModel::class) {

    private fun login() {
        val (login, password, gsrKey) = editTextHandler()
        vm.login(login, password, gsrKey)
    }

    private fun editTextHandler(): Array<String> {
        val login = getTrimmedText(vb.etLogin)
        val password = getTrimmedText(vb.etPassword)
        val gsrKey = getTrimmedText(vb.etGsrKey)
        return arrayOf(login, password, gsrKey)
    }

    override fun setupViews() {
        vb.progressbar.visible(false)
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
        // LOGIN ---> 10480_2 PASSWORD ---> Kr1_7W GSR-KEY ---> 910287
        vm.event.observe(this, Observer {
            vb.progressbar.visible(it !is AuthUser)
            when (it) {
                is AuthUser -> {
                    setResult(AUTH_RESULT)
                    finish()
                }
            }
        })
    }

    override fun getBinding() = AuthActivityBinding.inflate(layoutInflater)

    companion object {
        const val AUTH_RESULT = 1
    }

    override fun onBackPressed() {
        return
    }
}
