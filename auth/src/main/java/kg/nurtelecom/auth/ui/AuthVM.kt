package kg.nurtelecom.auth.ui

import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.auth.repository.AuthRepository
import kg.nurtelecom.core.CoreEvent
import kotlinx.coroutines.Dispatchers

abstract class AuthViewModel : CoreViewModel() {
    abstract fun login(username: String, password: String, gsrKey: String, isFiscalRegime: Boolean)
}

class AuthViewModelImpl (private val authRepository: AuthRepository) : AuthViewModel() {

    override fun login(username: String, password: String, gsrKey: String, isFiscalRegime: Boolean)  {
        safeCall {
            val result = authRepository.fetchAccessToken(username, password, gsrKey, isFiscalRegime)
            event.postValue(AuthUser(result.access_token))
            fetchUserData()
            fetchSecureKey()
        }
    }

    private suspend fun fetchUserData() {
        safeCall(Dispatchers.IO) {
            authRepository.fetchUserData()
        }
    }

    private suspend fun fetchSecureKey() {
        safeCall(Dispatchers.IO) {
            authRepository.fetchSecureKey()
        }
    }
}

class AuthUser(val access_token: String) : CoreEvent()