package kg.nurtelecom.auth.ui

import kg.nurtelecom.auth.repository.AuthRepository
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kotlinx.coroutines.Dispatchers

abstract class AuthViewModel : CoreViewModel() {
    abstract fun login(username: String, password: String, gsrKey: String, isFiscalRegime: Boolean)
}

class AuthViewModelImpl (private val authRepository: AuthRepository) : AuthViewModel() {

    override fun login(username: String, password: String, gsrKey: String, isFiscalRegime: Boolean)  {
        safeCall {
            authRepository.fetchAccessToken(username, password, gsrKey, isFiscalRegime)
            fetchUserData()
        }
    }

    private suspend fun fetchUserData() {
        safeCall(Dispatchers.IO) {
            authRepository.fetchUserData()
        }
    }
}