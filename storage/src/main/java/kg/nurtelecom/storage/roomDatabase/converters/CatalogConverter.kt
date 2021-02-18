package kg.nurtelecom.storage.roomDatabase.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kg.nurtelecom.data.sell.ProductCatalog

class CatalogConverter {

    @TypeConverter
    fun stringToCatalog(json: String): ProductCatalog {
        val gson = Gson()
        val type = object : TypeToken<ProductCatalog>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun catalogToString(catalog: ProductCatalog): String {
        val gson = Gson()
        val type = object : TypeToken<ProductCatalog>() {}.type
        return gson.toJson(catalog, type)
    }

}