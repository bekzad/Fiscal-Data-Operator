package kg.nurtelecom.storage.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kg.nurtelecom.data.UserDetail
import kg.nurtelecom.data.sell.CatalogResult
import kg.nurtelecom.storage.roomDatabase.converters.BusinessTypeConverter
import kg.nurtelecom.storage.roomDatabase.converters.CatalogConverter
import kg.nurtelecom.storage.roomDatabase.converters.ProductConverter
import kg.nurtelecom.storage.roomDatabase.converters.TaxPayerConverter

@Database(entities = [UserDetail::class, CatalogResult::class], version = 3, exportSchema = false)
@TypeConverters(CatalogConverter::class, ProductConverter::class, TaxPayerConverter::class, BusinessTypeConverter::class)
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
