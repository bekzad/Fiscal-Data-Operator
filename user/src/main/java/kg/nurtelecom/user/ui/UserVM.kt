package kg.nurtelecom.user.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.UserDetail
import kg.nurtelecom.user.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class UserVM : CoreViewModel() {

    abstract var userData: MutableLiveData<UserDetail>
    abstract fun fetchUserData()
    abstract fun updateUserData(surname: String, name: String, middleName: String , phone: String, inn: String)
}
class UserVMImpl(private val repository: UserRepository): UserVM(){

    override var  userData: MutableLiveData<UserDetail> = MutableLiveData()

    override fun updateUserData(surname: String, name: String, middleName: String , phone: String, inn: String) {
        safeCall (Dispatchers.IO){
            val answer = repository.updateApiUserProfile(surname, name, middleName, phone, inn)
            repository.updateLocalUserProfile(answer.result)
            fetchUserData()
        }
    }

    override fun fetchUserData(){
        viewModelScope.launch(Dispatchers.IO){
            userData.postValue(repository.fetchLocalUserProfile())
        }
    }
}




