package kg.nurtelecom.auth.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.AccessToken
import kg.nurtelecom.core.Resource
import kg.nurtelecom.auth.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class AuthViewModel : CoreViewModel() {
    abstract val loginResponse: MutableLiveData<Resource<AccessToken>>
    abstract fun login(username: String, password: String, gsrKey: String)
}

class AuthViewModelImpl (private val authRepository: AuthRepository) : AuthViewModel() {
    override val loginResponse: MutableLiveData<Resource<AccessToken>> = MutableLiveData()

    override fun login(username: String, password: String, gsrKey: String)  {
        safeCall {
            loginResponse.postValue(Resource.Loading)
            loginResponse.postValue(authRepository.fetchAccessToken(username, password, gsrKey))
            fetchUserData()
        }
    }

    private suspend fun fetchUserData() {
        safeUICall {
            authRepository.fetchUserData()
        }
    }
}