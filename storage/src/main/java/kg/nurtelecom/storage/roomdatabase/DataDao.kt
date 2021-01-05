package kg.nurtelecom.storage.roomdatabase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import kg.nurtelecom.data.UserDetail
import kg.nurtelecom.data.history.Content

@Dao
interface DataDao {

    //запись в БД.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userDetailModel: UserDetail)

    //удаление
    @Delete
    fun delete(userDetailModel: UserDetail)

    //запрос
    @Query("SELECT * FROM user")
    fun getAllData(): LiveData<List<UserDetail>>

    //обнов.
     @Update
     fun updateUserData(userDetailModel: UserDetail)
}
