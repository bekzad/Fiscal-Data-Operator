package kg.nurtelecom.sell.repository

import kg.nurtelecom.data.sell.CatalogResult
import kg.nurtelecom.network.data.api.ProductApi
import kg.nurtelecom.storage.sharedpref.AppPreferences

class SellRepository(private val appPrefs: AppPreferences, private val api: ProductApi) {

    val fetchRegime: Boolean get() = appPrefs.fiscalRegime

    suspend fun fetchProductCategory(): List<CatalogResult> {
        return api.fetchProductCatalog("Bearer ${appPrefs.token}").result
    }
}