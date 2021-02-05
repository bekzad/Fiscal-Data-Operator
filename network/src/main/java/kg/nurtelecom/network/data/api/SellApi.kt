package kg.nurtelecom.network.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SellApi {

    @POST("receipt")
    suspend fun fetchReceipt (
        @Header("Authorization") accessToken: String,
        @Header("session_uuid") uuid: String,
        @Body fetchReceiptRequest: String
    ): Response<String>

}