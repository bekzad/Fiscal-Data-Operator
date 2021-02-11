package kg.nurtelecom.storage.roomDatabase.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kg.nurtelecom.data.sell.Products

class ProductConverter {

    @TypeConverter
    fun stringToProduct(json: String): Products {
        val gson = Gson()
        val type = object : TypeToken<Products>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun productToString(products: Products): String {
        val gson = Gson()
        val type = object : TypeToken<Products>() {}.type
        return gson.toJson(products, type)
    }

}