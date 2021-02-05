package kg.nurtelecom.sell.repository

import kg.nurtelecom.data.sell.CatalogResult
import kg.nurtelecom.network.data.api.SellApi
import kg.nurtelecom.storage.sharedpref.AppPreferences
import retrofit2.Response

class SellRepository(
    private val sellApi: SellApi,
    private val appPrefs: AppPreferences) {

    val isNonFiscalRegime: Boolean get() = appPrefs.fiscalRegime

    suspend fun fetchProductCategory(): List<CatalogResult> {
        return sellApi.fetchProductCatalog("Bearer ${appPrefs.token}").result
    }

    suspend fun fetchReceipt(fetchReceiptRequest: String): Response<String> {
        return sellApi.fetchReceipt(accessToken = "Bearer ${appPrefs.token}",
                                            uuid = appPrefs.token,
                                            fetchReceiptRequest = fetchReceiptRequest)
    }

}