package kg.nurtelecom.storage.roomDatabase.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kg.nurtelecom.data.sell.TaxPayer

class TaxPayerConverter {

    @TypeConverter
    fun stringToProduct(json: String): TaxPayer {
        val gson = Gson()
        val type = object : TypeToken<TaxPayer>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun productToString(businessType: TaxPayer): String {
        val gson = Gson()
        val type = object : TypeToken<TaxPayer>() {}.type
        return gson.toJson(type, type)
    }
}