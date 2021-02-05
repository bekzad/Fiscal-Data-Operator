package kg.nurtelecom.sell.repository

import kg.nurtelecom.data.sell.CatalogResult
import kg.nurtelecom.network.data.api.SellApi
import kg.nurtelecom.storage.sharedpref.AppPreferences

class SellRepository(private val appPrefs: AppPreferences, private val api: SellApi) {

    val isNonFiscalRegime: Boolean get() = appPrefs.fiscalRegime

    suspend fun fetchProductCategory(): List<CatalogResult> {
        return api.fetchProductCatalog("Bearer ${appPrefs.token}").result
    }
}