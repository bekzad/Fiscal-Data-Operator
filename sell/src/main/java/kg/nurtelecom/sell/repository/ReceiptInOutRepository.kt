package kg.nurtelecom.sell.repository

import android.util.Log
import com.google.gson.Gson
import kg.nurtelecom.data.receipt_in_out.*
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

    suspend fun fetchReceiptInOutHistoryList(requestBody: ReceiptInOutHistoryRequest): ReceiptInOutHistoryRequestResult {
        val gson = Gson()
        val jsonString = gson.toJson(requestBody)
        val result = api.fetchReceiptInOutHistoryList("Bearer ${appPref.token}", jsonString)
        return gson.fromJson(result, ReceiptInOutHistoryRequestResult::class.java)
    }
}