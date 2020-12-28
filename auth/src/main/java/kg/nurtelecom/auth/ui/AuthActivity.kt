package kg.nurtelecom.auth.ui

import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import kg.nurtelecom.auth.databinding.AuthActivityBinding
import kg.nurtelecom.core.activity.CoreActivity
import kg.nurtelecom.core.extension.getTrimmedText
import kg.nurtelecom.core.extension.enable

class AuthActivity : CoreActivity<AuthActivityBinding, AuthViewModel>(AuthViewModel::class) {

    private fun login() {
        val (login, password, gsrKey) = editTextHandler()
        vm.login(login, password, gsrKey)
    }

    private fun editTextHandler(): Array<String> {
        val login = vb.etLogin.getTrimmedText()
        val password = vb.etPassword.getTrimmedText()
        val gsrKey = vb.etGsrKey.getTrimmedText()
        return arrayOf(login, password, gsrKey)
    }

    override fun setupViews() {
        vb.btnLogin.enable(false)

//        vb.etLogin.addTextChangedListener {
//            val (login, password, gsrKey) = editTextHandler()
//            vb.btnLogin.enable(login.isNotEmpty() && password.isNotEmpty() && gsrKey.isNotEmpty())
//        }

        vb.etPassword.addTextChangedListener {
            val (login, password, gsrKey) = editTextHandler()
            vb.btnLogin.enable(login.isNotEmpty() && password.isNotEmpty() && gsrKey.isNotEmpty())
        }

        vb.etGsrKey.addTextChangedListener {
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
        vm.event.observe(this, Observer {
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
