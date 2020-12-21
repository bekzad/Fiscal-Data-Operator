package kg.nurtelecom.auth.ui

import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.auth.repository.AuthRepository
import kg.nurtelecom.core.CoreEvent

abstract class AuthViewModel : CoreViewModel() {
    abstract fun login(username: String, password: String, gsrKey: String)
}

class AuthViewModelImpl (private val authRepository: AuthRepository) : AuthViewModel() {

    override fun login(username: String, password: String, gsrKey: String)  {
        safeCall {
            val result = authRepository.fetchAccessToken(username, password, gsrKey)
            event.postValue(AuthUser(result.access_token))
            fetchUserData()
        }
    }

    private suspend fun fetchUserData() {
        safeUICall {
            authRepository.fetchUserData()
        }
    }
}
class AuthUser(val access_token: String) : CoreEvent()