package kg.nurtelecom.user.repository

import kg.nurtelecom.data.UserDetail
import kg.nurtelecom.storage.roomDatabase.DataDao

class UserRepository(private val dataDao: DataDao) {

    suspend fun fetchUserData(): UserDetail {
        return dataDao.getUserData()
    }
}










