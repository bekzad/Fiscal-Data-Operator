package kg.nurtelecom.storage.roomDatabase.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kg.nurtelecom.data.sell.BusinessType

class BusinessTypeConverter {

    @TypeConverter
    fun stringToProduct(json: String): BusinessType {
        val gson = Gson()
        val type = object : TypeToken<BusinessType>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun productToString(businessType: BusinessType): String {
        val gson = Gson()
        val type = object : TypeToken<BusinessType>() {}.type
        return gson.toJson(type, type)
    }
}