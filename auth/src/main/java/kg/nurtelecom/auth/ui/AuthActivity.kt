package kg.nurtelecom.auth.ui

import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import kg.nurtelecom.auth.databinding.AuthActivityBinding
import kg.nurtelecom.core.activity.CoreActivity
import kg.nurtelecom.core.extension.text
import kg.nurtelecom.core.extension.enable
import kg.nurtelecom.core.extension.handleApiError
import kg.nurtelecom.core.extension.visible

class AuthActivity : CoreActivity<AuthActivityBinding, AuthViewModel>(AuthViewModel::class) {

    private fun login() {
        val (login, password, gsrKey) = editTextHandler()
        vm.login(login, password, gsrKey)
    }

    private fun editTextHandler(): Array<String> {

        val login = text(vb.etLogin)
        val password = text(vb.etPassword)
        val gsrKey = text(vb.etGsrKey)
        return arrayOf(login, password, gsrKey)
    }

    override fun setupViews() {
        vb.progressbar.visible(false)
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
        // LOGIN ---> 10480_2 PASSWORD ---> Kr1_7W GSR-KEY ---> 910287
        vm.event.observe(this, Observer {
//            vb.progressbar.visible(it is Resource.Loading)
            when (it) {
                is AuthUser -> {
                    if (it.access_token.isNotEmpty()){
                        setResult(AUTH_RESULT)
                        finish()
                    } else {
                        handleApiError(vb.root) {login()}
                    }
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
