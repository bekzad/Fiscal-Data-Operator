package kg.nurtelecom.user.ui

import androidx.lifecycle.MutableLiveData
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.UserDetailModel
import kg.nurtelecom.user.repository.UserRepository

abstract class UserVM : CoreViewModel() {

 abstract var userData: MutableLiveData<UserDetailModel>
}

    class UserVMImpl: UserVM(){

    override var  userData: MutableLiveData<UserDetailModel> = MutableLiveData()

    }

