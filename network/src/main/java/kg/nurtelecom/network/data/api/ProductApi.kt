package kg.nurtelecom.network.data.api

import kg.nurtelecom.data.sell.ProductCategory
import retrofit2.http.GET
import retrofit2.http.Header

interface ProductApi {
    @GET("catalog/category")
    suspend fun fetchProductCatalog(@Header("Authorization") token: String): ProductCategory
}