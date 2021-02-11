package kg.nurtelecom.storage.roomDatabase.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kg.nurtelecom.data.sell.Products

class ProductConverter {

    @TypeConverter
    fun stringToProduct(json: String): List<Products> {
        val gson = Gson()
        val type = object : TypeToken<List<Products>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun productToString(products: List<Products>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Products>>() {}.type
        return gson.toJson(products, type)
    }

}