package kg.nurtelecom.sell.repository

import kg.nurtelecom.data.z_report.ReportDetailed
import kg.nurtelecom.network.data.api.ReportsApi
import kg.nurtelecom.storage.sharedpref.AppPreferences


class SessionRepository(
    private val reportsApi: ReportsApi,
    private val appPrefs: AppPreferences
) {
    suspend fun fetchReportSession() : ReportDetailed {
        return reportsApi.fetchReportSession(
            "Bearer ${appPrefs.token}", appPrefs.token
        )
    }

    suspend fun closeSession() : ReportDetailed {
        return reportsApi.closeSession(
            "Bearer ${appPrefs.token}", appPrefs.token
        )
    }

    fun fetchCurrentDate() = appPrefs.currentDate
}