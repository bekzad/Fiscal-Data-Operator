package kg.nurtelecom.sell.ui.activity

import androidx.lifecycle.MutableLiveData
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.data.history_by_id.Result
import kg.nurtelecom.data.sell.AllProducts
import kg.nurtelecom.data.sell.CatalogResult
import kg.nurtelecom.data.sell.Product
import kg.nurtelecom.data.sell.Products
import kg.nurtelecom.sell.repository.SellRepository
import kg.nurtelecom.data.z_report.ReportDetailed
import kg.nurtelecom.sell.repository.HistoryRepository
import kg.nurtelecom.sell.repository.SessionRepository
import kg.nurtelecom.sell.utils.roundUp
import kotlinx.coroutines.Dispatchers
import java.math.BigDecimal

abstract class SellMainViewModel : CoreViewModel() {

    abstract val productList: MutableLiveData<MutableList<Product>>
    abstract val taxSum: MutableLiveData<BigDecimal>
    abstract val selectedProductData: MutableLiveData<AllProducts>
    abstract var isProductEmpty: MutableLiveData<Boolean>
    abstract val productCatalog: MutableLiveData<List<CatalogResult>>
    abstract val isRegimeNonFiscal: Boolean
    abstract fun addNewProduct(product: Product)
    abstract fun removeProduct(position: Int)
    abstract fun sendSelectedProduct(product: AllProducts)
    abstract fun fetchProductCatalog()
    abstract fun clearSelectedProduct()
    abstract fun searchProduct(name: String)
    open val filteredProducts: MutableLiveData<List<Products>>? = MutableLiveData()

    // Checks History
    abstract var checksHistoryData: MutableLiveData<List<Content>>
    abstract var detailCheckHistory: MutableLiveData<Result>
    abstract fun fetchChecksHistory()
    abstract fun fetchDetailCheckHistory(id: Int)

    // Session
    abstract var sessionReportData: MutableLiveData<ReportDetailed>
    abstract fun fetchReportSession()
    abstract fun closeSession()
}

class SellMainViewModelImpl(
    private val historyRepository: HistoryRepository,
    private val sessionRepository: SessionRepository,
    private val sellRepository: SellRepository
) : SellMainViewModel() {

    override val productList: MutableLiveData<MutableList<Product>> =
        MutableLiveData(mutableListOf())

    override val taxSum: MutableLiveData<BigDecimal> = MutableLiveData()

    override val selectedProductData: MutableLiveData<AllProducts> = MutableLiveData()

    override var isProductEmpty: MutableLiveData<Boolean> = MutableLiveData(true)

    override val isRegimeNonFiscal: Boolean = sellRepository.isNonFiscalRegime

    override val productCatalog: MutableLiveData<List<CatalogResult>> = MutableLiveData(listOf())

    init {
        println("INIT SELLMAINVM")
        fetchProductCatalog()
    }

    override fun addNewProduct(product: Product) {
        productList.value?.add(product)
        taxSum.value = calculateTaxSum()
        isProductEmpty.value = false
    }

    override fun fetchProductCatalog() {
        if (!sellRepository.isNonFiscalRegime) {
            safeCall(Dispatchers.IO) {
                productCatalog.postValue(sellRepository.fetchProductCategory())
            }
        }
    }

    private fun calculateTaxSum(): BigDecimal {
        var taxSum = BigDecimal.ZERO
        var totalPrice: BigDecimal
        var tax: BigDecimal
        val taxRate = BigDecimal("12.0")
        val hundred = BigDecimal("100.0")

        for (product in productList.value ?: listOf()) {
            totalPrice = product.totalPrice
            tax = (totalPrice.multiply(taxRate).divide(hundred))
            taxSum = taxSum.add(totalPrice).add(tax)
        }

        return taxSum.roundUp()
    }

    override fun removeProduct(position: Int) {
        productList.value?.removeAt(position)
        taxSum.value = calculateTaxSum()
        if (productList.value.isNullOrEmpty()) {
            isProductEmpty.value = true
        }
    }

    override fun sendSelectedProduct(product: AllProducts) {
        selectedProductData.value = product
    }

    override fun clearSelectedProduct() {
        selectedProductData.value = null
    }

    override fun searchProduct(name: String) {
        val searchList = mutableListOf<Products>()
        productCatalog.value?.let { list ->
            list.forEach { catalog ->
                catalog.products.forEach { product ->
                    searchList.add(product)
                }
            }
            val filteredList = searchList.filter { products ->
                products.name.contains(name, true)
            }
            filteredProducts?.value = filteredList
        }
    }

    // Checks History
    override var checksHistoryData: MutableLiveData<List<Content>> = MutableLiveData()
    override var detailCheckHistory: MutableLiveData<Result> = MutableLiveData()

    override fun fetchChecksHistory() {
        safeCall(Dispatchers.IO) {
            checksHistoryData.postValue(historyRepository.fetchChecksHistory())
        }
    }

    override fun fetchDetailCheckHistory(id: Int) {
        safeCall(Dispatchers.IO) {
            detailCheckHistory.postValue(historyRepository.fetchDetailCheckHistory(id))
        }
    }

    // Session
    override var sessionReportData: MutableLiveData<ReportDetailed> = MutableLiveData()

    override fun fetchReportSession() {
        safeCall(Dispatchers.IO) {
            sessionReportData.postValue(sessionRepository.fetchReportSession())
        }
    }

    override fun closeSession() {
        safeCall(Dispatchers.IO) {
            sessionReportData.postValue(sessionRepository.closeSession())
        }
    }
}