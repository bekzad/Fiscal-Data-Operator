package kg.nurtelecom.storage.roomdatabase

import androidx.room.*
import kg.nurtelecom.data.UserDetail

@Dao
interface DataDao {

    //запись в БД.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userDetail: UserDetail)

    //удаление
    @Delete
    fun delete(userDetail: UserDetail)

    //запрос
    @Query("SELECT * FROM user")
    fun getUserData(): UserDetail

    //обнов.
     @Update
     fun updateUserData(userDetail: UserDetail)

}
