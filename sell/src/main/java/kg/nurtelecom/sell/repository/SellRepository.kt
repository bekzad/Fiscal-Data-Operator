package kg.nurtelecom.sell.repository

import kg.nurtelecom.data.LogoutResult
import kg.nurtelecom.data.sell.CatalogResult
import kg.nurtelecom.data.sell.ProductCategory
import kg.nurtelecom.network.data.api.AuthorizationApi
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
    private val sellDao: SellDao,
    private val authorizationApi: AuthorizationApi
) {
    val isNonFiscalRegime: Boolean get() = appPrefs.fiscalRegime
    val isDialogShow: Boolean get() = appPrefs.isFiscalDialogShow

    val catalogFromLocal: Flow<List<CatalogResult>> = sellDao.fetchProductCatalog()

    fun changeDialogPref(value: Boolean) {
        appPrefs.isFiscalDialogShow = value
    }

    suspend fun fetchProductCategoryRemotely(): Response<ProductCategory> {
        return productApi.fetchProductCatalog("Bearer ${appPrefs.token}")
    }

    suspend fun saveCatalogToDatabase(catalog: List<CatalogResult>) {
        sellDao.insertProductCatalog(catalog)
    }

    suspend fun fetchReceipt(fetchReceiptRequest: String): Response<String> {
        return sellApi.fetchReceipt(
            accessToken = "Bearer ${appPrefs.token}",
            uuid = appPrefs.token,
            fetchReceiptRequest = fetchReceiptRequest
        )
    }

    suspend fun logout(): LogoutResult {
        val result = authorizationApi.logout(appPrefs.token)
        appPrefs.token = ""
        appPrefs.refreshToken = ""
        appPrefs.secureKey = ""
        return result
    }
}