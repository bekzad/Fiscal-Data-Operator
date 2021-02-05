package kg.nurtelecom.network.data.api

import kg.nurtelecom.data.sell.ProductCategory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SellApi {

    @GET("catalog/category")
    suspend fun fetchProductCatalog(@Header("Authorization") token: String): ProductCategory

    @POST("receipt")
    suspend fun fetchReceipt (
        @Header("Authorization") accessToken: String,
        @Header("session_uuid") uuid: String,
        @Body fetchReceiptRequest: String
    ): Response<String>

}