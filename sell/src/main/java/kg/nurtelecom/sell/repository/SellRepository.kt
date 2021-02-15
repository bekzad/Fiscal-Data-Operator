package kg.nurtelecom.sell.repository

import kg.nurtelecom.data.LogoutResult
import kg.nurtelecom.data.sell.ProductCategory
import kg.nurtelecom.network.data.api.AuthorizationApi
import kg.nurtelecom.network.data.api.ProductApi
import kg.nurtelecom.network.data.api.SellApi
import kg.nurtelecom.storage.sharedpref.AppPreferences
import retrofit2.Response

class SellRepository(
    private val sellApi: SellApi,
    private val productApi: ProductApi,
    private val authorizationApi: AuthorizationApi,
    private val appPrefs: AppPreferences) {

    val isNonFiscalRegime: Boolean get() = appPrefs.fiscalRegime

    suspend fun fetchProductCategory(): Response<ProductCategory> {
        return productApi.fetchProductCatalog("Bearer ${appPrefs.token}")
    }

    suspend fun fetchReceipt(fetchReceiptRequest: String): Response<String> {
        return sellApi.fetchReceipt(accessToken = "Bearer ${appPrefs.token}",
                                            uuid = appPrefs.token,
                                            fetchReceiptRequest = fetchReceiptRequest)
    }

    suspend fun logout(): LogoutResult {
        val result = authorizationApi.logout(appPrefs.token)
        appPrefs.token = ""
        appPrefs.refreshToken = ""
        appPrefs.secureKey = ""
        return result
    }
}