package kg.nurtelecom.sell.repository

import android.util.Log
import com.google.gson.Gson
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutRequest
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutRequestResult
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutType
import kg.nurtelecom.network.data.api.ReceiptInOutApi
import kg.nurtelecom.storage.sharedpref.AppPreferences
import java.math.BigDecimal

class ReceiptInOutRepository(
        private val api: ReceiptInOutApi,
        private val appPref: AppPreferences) {

    suspend fun generateReceiptInOut(requestBody: ReceiptInOutRequest): ReceiptInOutRequestResult {
        val gson = Gson()
        val jsonString = gson.toJson(requestBody)
        val result = api.generateReceiptInOut("Bearer ${appPref.token}", jsonString)
        return gson.fromJson(result, ReceiptInOutRequestResult::class.java)
    }

}