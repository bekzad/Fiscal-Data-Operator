package kg.nurtelecom.network.data.api

import retrofit2.http.*

interface ReceiptInOutApi {

    @POST("receiptinout")
    suspend fun generateReceiptInOut(
            @Header("Authorization") accessToken: String,
            @Body receiptInOutRequest: String
    ): String

    @POST("receiptinout/history")
    suspend fun fetchReceiptInOutHistoryList(
        @Header("Authorization") accessToken: String,
        @Body receiptInOutHistoryRequest: String
    ): String

    @POST("receiptinout/history/{id}")
    suspend fun fetchReceiptInOutById(
            @Header("Authorization") accessToken: String,
            @Path("id") id: Long
    ): String
}