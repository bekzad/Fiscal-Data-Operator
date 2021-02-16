package kg.nurtelecom.sell.repository

import com.google.gson.Gson
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutRequest
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutType
import kg.nurtelecom.network.data.api.ReceiptInOutApi
import kg.nurtelecom.network.data.api.SellApi
import kg.nurtelecom.storage.sharedpref.AppPreferences
import java.math.BigDecimal

class ReceiptInOutRepository(
        private val api: ReceiptInOutApi,
        private val appPref: AppPreferences) {

    suspend fun generateReceiptInOut(): String {

        val gson = Gson()
        val jsonString = gson.toJson(ReceiptInOutRequest(null, ReceiptInOutType.INCOME, BigDecimal(300)))

        return api.generateReceiptInOut("Bearer ${appPref.token}", jsonString)
    }

}