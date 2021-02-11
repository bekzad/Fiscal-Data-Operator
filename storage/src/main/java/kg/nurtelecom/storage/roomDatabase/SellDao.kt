package kg.nurtelecom.storage.roomDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kg.nurtelecom.data.sell.CatalogResult
import kotlinx.coroutines.flow.Flow

@Dao
interface SellDao {

    @Query("SELECT * FROM catalog")
    fun fetchProductCatalog(): Flow<List<CatalogResult>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductCatalog(catalog: List<CatalogResult>)
}