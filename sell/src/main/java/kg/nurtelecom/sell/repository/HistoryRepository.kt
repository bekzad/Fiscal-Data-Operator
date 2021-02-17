package kg.nurtelecom.sell.repository

import kg.nurtelecom.core.extension.subtractDays
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.data.history.DateBody
import kg.nurtelecom.data.history_by_id.Result
import kg.nurtelecom.network.data.api.HistoryApi
import kg.nurtelecom.storage.sharedpref.AppPreferences
import java.text.SimpleDateFormat
import java.util.*

class HistoryRepository(
    private val historyApi: HistoryApi,
    private val appPrefs: AppPreferences
) {
    suspend fun fetchChecksHistory(operationType: String? = null) : List<Content> {
        val checksHistory = historyApi.fetchChecksHistory(
            "Bearer ${appPrefs.token}", DateBody(
                SimpleDateFormat(DATE_FORMAT, Locale("ru")).format(Date().subtractDays(AMOUNT_OF_DAYS)),
                SimpleDateFormat(DATE_FORMAT, Locale("ru")).format(Date()),
                operationType
            ))
        return checksHistory.result.content
    }

    suspend fun fetchDetailCheckHistory(id: Int) : Result {
        val detailCheckHistory = historyApi.fetchCheckHistoryById(
            "Bearer ${appPrefs.token}", id)
        return detailCheckHistory.result
    }

    companion object Keys {
        private const val AMOUNT_OF_DAYS = 14
        private const val DATE_FORMAT = "dd.MM.yyyy HH:mm:ss"
    }
}