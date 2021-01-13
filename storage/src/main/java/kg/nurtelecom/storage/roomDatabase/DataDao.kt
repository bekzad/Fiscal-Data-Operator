package kg.nurtelecom.storage.roomdatabase

import androidx.room.*
import kg.nurtelecom.data.UserDetail

@Dao
interface DataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userDetail: UserDetail)

    @Delete
    fun delete(userDetail: UserDetail)

    @Query("SELECT * FROM user")
    fun getUserData(): UserDetail

     @Update
     fun updateUserData(userDetail: UserDetail)
}
