package kg.nurtelecom.user.repository

import android.util.Log
import kg.nurtelecom.data.UserDetail
import kg.nurtelecom.network.data.api.UserApi
import kg.nurtelecom.storage.roomDatabase.DataDao
import kg.nurtelecom.storage.sharedpref.AppPreferences

class UserRepository(private val dataDao: DataDao, private val userApi: UserApi, private val appPrefs: AppPreferences) {

    suspend fun fetchUserData(): UserDetail {
        return dataDao.getUserData()

    }

    suspend fun updateUserData(surname: String, name: String, middleName: String , phone: String, inn: String){
        Log.i("Aki", appPrefs.token)
        val answer = userApi.sendingData("Bearer ${appPrefs.token}", phone, surname, name, middleName, inn )
        if (answer.resultCode == "SUCCESS"){
            val user = dataDao.getUserData()
            val updateUser = UserDetail(user.id, phone, surname, name, middleName, inn)
        dataDao.updateUserData(updateUser)
        }else{
            println("Ошибка ответа")
        }
    }
}










