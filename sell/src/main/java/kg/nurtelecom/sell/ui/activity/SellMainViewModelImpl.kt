package kg.nurtelecom.sell.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.google.gson.Gson
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.enums.OperationType
import kg.nurtelecom.data.receipt.result.FetchReceiptResult
import kg.nurtelecom.data.sell.*
import kg.nurtelecom.data.z_report.ReportDetailed
import kg.nurtelecom.sell.repository.SellRepository
import kg.nurtelecom.sell.repository.SessionRepository
import kg.nurtelecom.sell.utils.roundUp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import retrofit2.Response
import java.math.BigDecimal

abstract class SellMainViewModel : CoreViewModel() {

    abstract val productList: MutableLiveData<MutableList<Product>>
    abstract val taxSum: MutableLiveData<BigDecimal>
    abstract val selectedProductData: MutableLiveData<Products>
    abstract var isProductEmpty: MutableLiveData<Boolean>
    abstract var productCatalog: LiveData<List<CatalogResult>>
    abstract val isRegimeNonFiscal: Boolean
    abstract val fetchReceiptResult: MutableLiveData<Response<FetchReceiptResult>>
    abstract val fetchReceiptResultString: MutableLiveData<Response<String>>
    abstract var operationType: OperationType
    abstract var sessionReportData: MutableLiveData<ReportDetailed>
    abstract val isDialogShow: MutableLiveData<Boolean>
    abstract val isSubmitBtnEnabled: Flow<Boolean>

    open val filteredProducts: MutableLiveData<List<Products>>? = MutableLiveData()

    abstract fun fetchReceipt(fetchReceiptRequest: String)
    abstract fun addNewProduct(product: Product)
    abstract fun removeProduct(position: Int)
    abstract fun sendSelectedProduct(product: Products)
    abstract fun setDialogVisibility(value: Boolean)
    abstract fun clearSelectedProduct()
    abstract fun searchProduct(name: String)
    abstract fun fetchProductCatalogRemotely()
    abstract fun fetchProductCatalogLocally()

    // user input validation
    abstract fun setProductPrice(price: String)
    abstract fun setProductCharge(charge: String)

    // Session
    abstract fun fetchReportSession()
    abstract fun closeSession()

    abstract fun fetchCurrentDate(): String
}

class SellMainViewModelImpl(
    private val sessionRepository: SessionRepository,
    private val sellRepository: SellRepository
) : SellMainViewModel() {

    override val isRegimeNonFiscal: Boolean = sellRepository.isNonFiscalRegime

    override val productList: MutableLiveData<MutableList<Product>> =
        MutableLiveData(mutableListOf())
    override val taxSum: MutableLiveData<BigDecimal> = MutableLiveData()
    override val selectedProductData: MutableLiveData<Products> = MutableLiveData()
    override var isProductEmpty: MutableLiveData<Boolean> = MutableLiveData(true)
    override var productCatalog: LiveData<List<CatalogResult>> = MutableLiveData(listOf())

    override val fetchReceiptResult: MutableLiveData<Response<FetchReceiptResult>> =
        MutableLiveData()
    override val fetchReceiptResultString: MutableLiveData<Response<String>> = MutableLiveData()

    override var operationType: OperationType = OperationType.SALE

    override val isDialogShow: MutableLiveData<Boolean> =
        MutableLiveData(sellRepository.isDialogShow)

    // user input validation
    private val productPrice: MutableLiveData<String> = MutableLiveData("")
    private val productCharge: MutableLiveData<String> = MutableLiveData("")

    override val isSubmitBtnEnabled: Flow<Boolean> = combine(
        productPrice.asFlow(),
        productCharge.asFlow()
    ) { price, charge ->

        var isPriceCorrect = false
        if (!productPrice.value.isNullOrEmpty()) {
            isPriceCorrect = price.isNotEmpty()
        }

        val isChargeCorrect = charge.length < 3

        return@combine isPriceCorrect and isChargeCorrect
    }

    init {
        fetchProductCatalogRemotely()
    }

    override fun setProductPrice(price: String) {
        productPrice.value = price
    }

    override fun setProductCharge(charge: String) {
        productCharge.value = charge
    }

    override fun setDialogVisibility(value: Boolean) {
        isDialogShow.value = value
        sellRepository.changeDialogPref(value)
    }

    override fun fetchCurrentDate() = sessionRepository.fetchCurrentDate()

    override fun addNewProduct(product: Product) {
        productList.value?.add(product)
        taxSum.value = calculateTaxSum()
        isProductEmpty.value = false
    }

    private fun calculateTaxSum(): BigDecimal {
        var taxSum = BigDecimal.ZERO
        var totalPrice: BigDecimal
        var tax: BigDecimal
        val taxRate = BigDecimal("12.0")
        val hundred = BigDecimal("100.0")

        for (product in productList.value ?: listOf()) {
            totalPrice = product.productUnitPrice
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

    override fun sendSelectedProduct(product: Products) {
        selectedProductData.value = product
    }

    override fun clearSelectedProduct() {
        selectedProductData.value = null
        productPrice.value = null
        productCharge.value = ""
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

    override fun fetchProductCatalogRemotely() {
        safeCall(Dispatchers.IO) {
            sellRepository.fetchProductCategoryRemotely()
        }
    }

    override fun fetchProductCatalogLocally() {
        safeCall(Dispatchers.IO) {
            productCatalog = sellRepository.catalogFromLocal.asLiveData()
        }
    }

    override fun fetchReceipt(fetchReceiptRequest: String) {
        safeCall(Dispatchers.IO) {
            val response = sellRepository.fetchReceipt(fetchReceiptRequest)
            fetchReceiptResultString.postValue(response)

            val gson = Gson()
            val jsonBody = response.body() ?: ""
            val jsonString = """${jsonBody}"""
//
//            val fetchReceiptResult = gson.fromJson(jsonString, FetchReceiptResult::java)
//            fetchReceiptResult.postValue()
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