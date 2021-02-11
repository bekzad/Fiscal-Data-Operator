package kg.nurtelecom.sell.repository

import kg.nurtelecom.data.sell.CatalogResult
import kg.nurtelecom.network.data.api.ProductApi
import kg.nurtelecom.network.data.api.SellApi
import kg.nurtelecom.storage.roomDatabase.SellDao
import kg.nurtelecom.storage.sharedpref.AppPreferences
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class SellRepository(
    private val sellApi: SellApi,
    private val productApi: ProductApi,
    private val appPrefs: AppPreferences,
    private val sellDao: SellDao) {

    val isNonFiscalRegime: Boolean get() = appPrefs.fiscalRegime

    suspend fun fetchProductCategoryRemotely() {
        sellDao.insertProductCatalog(productApi.fetchProductCatalog("Bearer ${appPrefs.token}").result)
    }

    suspend fun fetchReceipt(fetchReceiptRequest: String): Response<String> {
        return sellApi.fetchReceipt(
                accessToken = "Bearer ${appPrefs.token}",
                uuid = appPrefs.token,
                fetchReceiptRequest = fetchReceiptRequest)
    }

    val catalogFromLocal: Flow<List<CatalogResult>> = sellDao.fetchProductCatalog()

}