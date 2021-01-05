package kg.nurtelecom.sell.repository

import kg.nurtelecom.core.extension.formatForServerDateTimeDefaults
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.data.history.DateBody
import kg.nurtelecom.network.data.api.HistoryApi
import kg.nurtelecom.storage.roomdatabase.DataDao
import kg.nurtelecom.storage.sharedpref.AppPreferences
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class SellRepository(
    private val historyApi: HistoryApi,
    private val appPrefs: AppPreferences
) {
    suspend fun fetchChecksHistory() : List<Content> {
        val checkHistory = historyApi.fetchChecksHistory(
            "Bearer ${appPrefs.token}", DateBody(
                LocalDateTime.now().minus(Keys.AMOUNT_OF_DAYS, ChronoUnit.DAYS).formatForServerDateTimeDefaults(),
                LocalDateTime.now().formatForServerDateTimeDefaults()))
        return checkHistory.result.content
    }

    object Keys {
        const val AMOUNT_OF_DAYS = 14L
    }
}
