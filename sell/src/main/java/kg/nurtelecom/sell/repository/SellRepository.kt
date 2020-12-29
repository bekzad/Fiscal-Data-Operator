package kg.nurtelecom.sell.repository

import android.util.Log
import kg.nurtelecom.data.history.DateBody
import kg.nurtelecom.network.data.api.HistoryApi
import kg.nurtelecom.storage.roomdatabase.DataDao
import kg.nurtelecom.storage.sharedpref.AppPreferences

class SellRepository(
    private val historyApi: HistoryApi,
    private val appPrefs: AppPreferences,
    private val dataDao: DataDao
) {
    suspend fun fetchCheckHistory() {
        // open issue
        val checkHistory = historyApi.fetchCheckHistory("Bearer ${appPrefs.token}", DateBody("15.12.2020 10:09:00", "29.12.2020 20:09:00"))
        dataDao.insertCheckHistory(checkHistory.result.content)
        Log.d("HISTORY", dataDao.getAllCheckHistory().toString())
    }
}
