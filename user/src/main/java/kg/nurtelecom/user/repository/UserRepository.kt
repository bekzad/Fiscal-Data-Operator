package kg.nurtelecom.user.repository

import kg.nurtelecom.data.UserDetail
import kg.nurtelecom.data.UserUpdateResult
import kg.nurtelecom.network.data.api.UserApi
import kg.nurtelecom.storage.roomDatabase.DataDao
import kg.nurtelecom.storage.sharedpref.AppPreferences

class UserRepository(private val dataDao: DataDao, private val userApi: UserApi, private val appPrefs: AppPreferences) {

    suspend fun fetchLocalUserProfile(): UserDetail {
        return dataDao.getUserProfile()
    }

    suspend fun updateApiUserProfile(surname: String, name: String, middleName: String , phone: String, inn: String): UserUpdateResult {
        val userDetail = UserDetail(null, phone, surname, name, middleName, inn)
        return userApi.updateUserProfile("Bearer ${appPrefs.token}", userDetail)
    }

    suspend fun updateLocalUserProfile(userDetail: UserDetail) {
        dataDao.updateUserProfile(userDetail)
    }
}