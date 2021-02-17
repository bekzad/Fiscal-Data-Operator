package kg.nurtelecom.network.data.api

import kg.nurtelecom.data.receipt_in_out.ReceiptInOutHistoryRequest
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutRequest
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutRequestResult
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

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
}