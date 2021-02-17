package kg.nurtelecom.sell.repository

import com.google.gson.Gson
import kg.nurtelecom.core.extension.subtractDays
import kg.nurtelecom.data.receipt_in_out.*
import kg.nurtelecom.network.data.api.ReceiptInOutApi
import kg.nurtelecom.storage.sharedpref.AppPreferences
import java.text.SimpleDateFormat
import java.util.*

class ReceiptInOutRepository(
        private val api: ReceiptInOutApi,
        private val appPref: AppPreferences) {

    private val gson = Gson()

    suspend fun generateReceiptInOut(requestBody: ReceiptInOutRequest): ReceiptInOutRequestResult {
        val jsonString = gson.toJson(requestBody)
        val result = api.generateReceiptInOut("Bearer ${appPref.token}", jsonString)
        return gson.fromJson(result, ReceiptInOutRequestResult::class.java)
    }

    suspend fun fetchReceiptInOutHistoryList(requestBody: ReceiptInOutHistoryRequest? = null): ReceiptInOutHistoryRequestResult {
        val jsonString = if (requestBody == null) {
            gson.toJson(ReceiptInOutHistoryRequest(
                    SimpleDateFormat(ReceiptInOutRepository.DATE_FORMAT, Locale("ru")).format(Date().subtractDays(ReceiptInOutRepository.AMOUNT_OF_DAYS)),
                    SimpleDateFormat(ReceiptInOutRepository.DATE_FORMAT, Locale("ru")).format(Date()),
                    null, null))
        } else {
            gson.toJson(requestBody)
        }

        val result = api.fetchReceiptInOutHistoryList("Bearer ${appPref.token}", jsonString)
        return gson.fromJson(result, ReceiptInOutHistoryRequestResult::class.java)
    }

    suspend fun fetchReceiptInOutById(id: Long): ReceiptInOutResult {
        val result = api.fetchReceiptInOutById("Bearer ${appPref.token}", id)
        return gson.fromJson(result, ReceiptInOutResult::class.java)
    }

    companion object {
        private const val AMOUNT_OF_DAYS = 14
        private const val DATE_FORMAT = "dd.MM.yyyy HH:mm:ss"
    }
}