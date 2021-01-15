package kg.nurtelecom.network.data.api

import kg.nurtelecom.data.history.DateBody
import kg.nurtelecom.data.history.HistoryResult
import kg.nurtelecom.data.history_by_id.HistoryResultById
import retrofit2.http.*

interface HistoryApi {
    @POST("receipt/history/")
    suspend fun fetchChecksHistory(
        @Header("Authorization") token: String,
        @Body dateBody: DateBody
    ): HistoryResult

    @POST("receipt/history/{id}/")
    suspend fun fetchCheckHistoryById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): HistoryResultById
}