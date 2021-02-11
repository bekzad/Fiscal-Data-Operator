package kg.nurtelecom.storage.roomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kg.nurtelecom.data.AccessToken
import kg.nurtelecom.data.Result
import kg.nurtelecom.data.UserDetail

@Database(entities = [UserDetail::class, AccessToken::class], version = 3)
abstract class RoomDB : RoomDatabase() {

    abstract fun getDataDao(): DataDao

    companion object {
        private const val DB_NAME: String = "ofdDB"
        private var instance: RoomDB? = null
        fun getInstance(context: Context): RoomDB {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration().build()
            }
            return instance!!
        }
    }
}
