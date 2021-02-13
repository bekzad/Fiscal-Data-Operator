package kg.nurtelecom.sell.ui.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.enums.OperationType
import kg.nurtelecom.data.receipt.result.FetchReceiptResult
import kg.nurtelecom.data.sell.AllProducts
import kg.nurtelecom.data.sell.CatalogResult
import kg.nurtelecom.data.sell.Product
import kg.nurtelecom.data.sell.Products
import kg.nurtelecom.data.z_report.ReportDetailed
import kg.nurtelecom.sell.repository.SellRepository
import kg.nurtelecom.sell.repository.SessionRepository
import kg.nurtelecom.sell.utils.observeKey
import kg.nurtelecom.sell.utils.roundUp
import kg.nurtelecom.storage.sharedpref.AppPreferences.Keys.ACCESS_TOKEN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.math.BigDecimal

abstract class SellMainViewModel : CoreViewModel() {

    abstract val productList: MutableLiveData<MutableList<Product>>
    abstract val taxSum: MutableLiveData<BigDecimal>
    abstract val sumWithNSP: LiveData<BigDecimal>
    abstract val selectedProductData: MutableLiveData<AllProducts>
    abstract var isProductEmpty: MutableLiveData<Boolean>
    abstract val productCatalog: MutableLiveData<List<CatalogResult>>
    abstract val isRegimeNonFiscal: Boolean
    abstract val fetchReceiptResult: MutableLiveData<FetchReceiptResult>
    abstract var operationType: OperationType
    abstract val amountPaid: MutableLiveData<BigDecimal>
    abstract val accessToken: LiveData<String>
    abstract val change: MutableLiveData<BigDecimal>

    abstract fun fetchReceipt(fetchReceiptRequest: String)
    abstract fun addNewProduct(product: Product)
    abstract fun removeProduct(position: Int)
    abstract fun sendSelectedProduct(product: AllProducts)
    abstract fun fetchProductCatalog()
    abstract fun clearSelectedProduct()
    abstract fun searchProduct(name: String)
    open val filteredProducts: MutableLiveData<List<Products>>? = MutableLiveData()

    // Session
    abstract var sessionReportData: MutableLiveData<ReportDetailed>
    abstract fun fetchReportSession()
    abstract fun closeSession()
}

class SellMainViewModelImpl(private val sessionRepository: SessionRepository,
    private val sellRepository: SellRepository) : SellMainViewModel() {

    @ExperimentalCoroutinesApi
    override val accessToken: LiveData<String>
        get() = sellRepository.observedToken.observeKey(ACCESS_TOKEN, "").asLiveData()

    override val productList: MutableLiveData<MutableList<Product>> =
        MutableLiveData(mutableListOf())

    override val taxSum: MutableLiveData<BigDecimal> = MutableLiveData(BigDecimal.ZERO)
    override val sumWithNSP: LiveData<BigDecimal>
        get() = calculateSumWithNSP()

    override val change: MutableLiveData<BigDecimal> = MutableLiveData(BigDecimal.ZERO)

    override val selectedProductData: MutableLiveData<AllProducts> = MutableLiveData()

    override var isProductEmpty: MutableLiveData<Boolean> = MutableLiveData(true)

    override val isRegimeNonFiscal: Boolean = sellRepository.isNonFiscalRegime

    override val productCatalog: MutableLiveData<List<CatalogResult>> = MutableLiveData(listOf())

    override val amountPaid: MutableLiveData<BigDecimal> = MutableLiveData(BigDecimal.ZERO)
    override val fetchReceiptResult: MutableLiveData<FetchReceiptResult> = MutableLiveData()
    override var operationType: OperationType = OperationType.SALE

    init {
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

        return taxSum
    }

    private fun calculateSumWithNSP(): LiveData<BigDecimal> {
        return MutableLiveData(taxSum.value?.multiply(BigDecimal("1.01")))
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

    override fun fetchReceipt(fetchReceiptRequest: String) {
        safeCall(Dispatchers.IO) {

            val response = sellRepository.fetchReceipt(fetchReceiptRequest)

            // We are casting the Json response into data classes and saving them to livedata
            val responseBody: String

            if (response.isSuccessful){
                responseBody = response.body() ?: "Successful response, null body"
            } else {
                responseBody = response.errorBody()?.string() ?: "Fail response, null error body"
            }

            try {
                val fetchReceiptResultTmp = Gson().fromJson(responseBody, FetchReceiptResult::class.java)
                fetchReceiptResult.postValue(fetchReceiptResultTmp)
            } catch (e: JsonSyntaxException) {
                Log.e("Gson", "Could not parse the responseBody $responseBody to FetchReceiptResult")
            }
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