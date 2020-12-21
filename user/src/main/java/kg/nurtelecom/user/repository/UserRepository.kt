package kg.nurtelecom.user.repository

import kg.nurtelecom.storage.roomDatabase.DataDao

class UserRepository(

    private val dataDao: DataDao


) {

//    private val dataDao: DataDao
//
////    constructor(application: Application){
////        val db = RoomDB.getInstance(application)
////        dataDao = db.getDataDao()
////        liveData = dataDao.getAllData()
//
//    }
//    fun getAllData(): MutableLiveData<List<UserDetail>>{
//        return liveData
//    }
//
//    fun insert(userDetail: UserDetail){
//        insertAsyncTask(dataDao).
//
//    }
//
//}
//private class insertAsyncTask (dao :DataDao): AsyncTask<UserDetail, Void, Void>() {
//
//    private val mAsyncTaskDao: DataDao = dao
//
//
//
//    override fun doInBackground(vararg userDetail: UserDetail): Void? {
//        mAsyncTaskDao.insert(userDetail[0])
//        return null
//
//    }
}



