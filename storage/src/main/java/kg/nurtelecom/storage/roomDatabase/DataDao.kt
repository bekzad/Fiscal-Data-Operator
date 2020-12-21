package kg.nurtelecom.storage.roomDatabase

import androidx.room.*
import kg.nurtelecom.data.UserDetail


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
    fun getAllData(): List<UserDetail>



}
