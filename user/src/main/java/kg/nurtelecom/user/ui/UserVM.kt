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
//        private val userRepository: UserRepository

    init {

//        userRepository = UserRepository()


    }

    fun userData(){

    }

//}
////fun WordViewModel(application:Application):??? {
//  super(application)
//  mRepository = WordRepository(application)
//  mAllWords = mRepository.getAllWords()
//}
//fun getAllWords():LiveData<List<Word>> {
//  return mAllWords
//}
//fun insert(word:Word) {
//  mRepository.insert(word)
}
