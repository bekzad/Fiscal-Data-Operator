package kg.nurtelecom.changepassword.ui

import androidx.lifecycle.MutableLiveData
import kg.nurtelecom.changepassword.repository.ChangePasswordRepository
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kotlinx.coroutines.Dispatchers

abstract class ChangePasswordViewModel : CoreViewModel() {
    abstract fun saveCurrentPassword(password: String)
    abstract val currentPassword: MutableLiveData<String>

    abstract fun changePassword(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    )
}

class ChangePasswordViewModelImpl(private val changePasswordRepository: ChangePasswordRepository) :
    ChangePasswordViewModel() {

    override val currentPassword: MutableLiveData<String> = MutableLiveData()

    override fun saveCurrentPassword(password: String) {
        currentPassword.value = password
    }

    override fun changePassword(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ) {
        safeCall(Dispatchers.IO) {
            val result = changePasswordRepository.changePassword(
                currentPassword,
                newPassword,
                confirmPassword
            )
            if (result) {
                println("You have changed your password successfully")
            } else {
                println("Try again")
            }
        }
    }
}