package kg.nurtelecom.sell.repository

import kg.nurtelecom.core.extension.formatForServerDateTimeDefaults
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.data.history.DateBody
import kg.nurtelecom.data.history_by_id.Result
import kg.nurtelecom.network.data.api.HistoryApi
import kg.nurtelecom.storage.sharedpref.AppPreferences
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class HistoryRepository(
    private val historyApi: HistoryApi,
    private val appPrefs: AppPreferences
) {
    suspend fun fetchChecksHistory() : List<Content> {
        val checksHistory = historyApi.fetchChecksHistory(
            "Bearer ${appPrefs.token}", DateBody(
                LocalDateTime.now().minus(Keys.AMOUNT_OF_DAYS, ChronoUnit.DAYS).formatForServerDateTimeDefaults(),
                LocalDateTime.now().formatForServerDateTimeDefaults()))
        return checksHistory.result.content
    }

    suspend fun fetchDetailCheckHistory(id: Int) : Result {
        val detailCheckHistory = historyApi.fetchCheckHistoryById(
            "Bearer ${appPrefs.token}", id)
        return detailCheckHistory.result
    }

    object Keys {
        const val AMOUNT_OF_DAYS = 14L
    }
}
