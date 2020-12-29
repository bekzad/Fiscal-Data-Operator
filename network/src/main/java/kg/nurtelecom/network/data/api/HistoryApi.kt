package kg.nurtelecom.network.data.api

import kg.nurtelecom.data.history.DateBody
import kg.nurtelecom.data.history.HistoryResult
import retrofit2.http.*

interface HistoryApi {
    @POST("receipt/history/")
    suspend fun fetchCheckHistory(
        @Header("Authorization") token: String,
        @Body dateBody: DateBody
    ): HistoryResult
}