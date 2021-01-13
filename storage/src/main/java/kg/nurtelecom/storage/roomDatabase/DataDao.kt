package kg.nurtelecom.storage.roomdatabase

import androidx.room.*
import kg.nurtelecom.data.UserDetail

@Dao
interface DataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userDetail: UserDetail)

    @Delete
    suspend fun delete(userDetail: UserDetail)

    @Query("SELECT * FROM user")
    suspend fun getUserProfile(): UserDetail

     @Update
     suspend fun updateUserProfile(userDetail: UserDetail)
}
