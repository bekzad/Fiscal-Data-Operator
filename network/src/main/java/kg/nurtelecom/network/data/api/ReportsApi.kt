package kg.nurtelecom.network.data.api

import kg.nurtelecom.data.z_report.ReportDetailed
import retrofit2.http.Header
import retrofit2.http.POST

interface ReportsApi {
    @POST("report/x-report/")
    suspend fun fetchReportSession(
        @Header("Authorization") token: String, @Header("session_uuid") session_uuid: String
    ): ReportDetailed

    @POST("report/z-report/")
    suspend fun closeSession(
        @Header("Authorization") token: String, @Header("session_uuid") session_uuid: String
    ): ReportDetailed
}