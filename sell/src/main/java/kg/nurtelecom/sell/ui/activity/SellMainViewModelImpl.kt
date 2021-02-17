package kg.nurtelecom.sell.ui.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.google.gson.*
import kg.nurtelecom.core.CoreEvent
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
import java.math.BigDecimal
import java.util.*


abstract class SellMainViewModel : CoreViewModel() {

    abstract val productList: MutableLiveData<MutableList<Product>>
    abstract val taxSum: MutableLiveData<BigDecimal>
    abstract val selectedProductData: MutableLiveData<Products>
    abstract var isProductEmpty: MutableLiveData<Boolean>
    abstract var productCatalog: LiveData<List<CatalogResult>>
    abstract val isRegimeNonFiscal: Boolean
    abstract val fetchReceiptResult: MutableLiveData<FetchReceiptResult>
    abstract var operationType: OperationType
    abstract var sessionReportData: MutableLiveData<ReportDetailed>
    abstract val isDialogShow: MutableLiveData<Boolean>
    abstract val isSubmitBtnEnabled: Flow<Boolean>
    abstract val ndsRate: MutableLiveData<BigDecimal>
    abstract val nspRate: MutableLiveData<BigDecimal>

    abstract val amountPaid: MutableLiveData<BigDecimal>
    abstract val change: MutableLiveData<BigDecimal>
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
    abstract fun updateTaxSum()

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

    override val productList: MutableLiveData<MutableList<Product>> = MutableLiveData(mutableListOf())
    override val selectedProductData: MutableLiveData<Products> = MutableLiveData()
    override var isProductEmpty: MutableLiveData<Boolean> = MutableLiveData(true)
    override var productCatalog: LiveData<List<CatalogResult>> = MutableLiveData(listOf())

    override val taxSum: MutableLiveData<BigDecimal> = MutableLiveData(BigDecimal.ZERO)
    override val ndsRate = MutableLiveData(BigDecimal("12.0"))
    override val nspRate = MutableLiveData(BigDecimal.ZERO)

    override val change: MutableLiveData<BigDecimal> = MutableLiveData(BigDecimal.ZERO)

    override val fetchReceiptResult: MutableLiveData<FetchReceiptResult> = MutableLiveData()

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

    override val amountPaid: MutableLiveData<BigDecimal> = MutableLiveData(BigDecimal.ZERO)

    // This is a first call to Api, it fetches the whole product catalog list
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
        updateTaxSum()
        isProductEmpty.value = false
    }
    override fun updateTaxSum() {
        taxSum.value = calculateTaxSum()
    }
    // This calculates the total sum with nds and nsp taxes discounts and charges
    private fun calculateTaxSum(): BigDecimal {
        val totalGoodsSum = calculateTotalGoodsSum()
        val ndsAmount = calculateNdsAmount(totalGoodsSum)
        val nspAmount = calculateNspAmount(totalGoodsSum)
        return totalGoodsSum.add(ndsAmount).add(nspAmount).roundUp()
    }

    // This is the amount of NDS tax totalGoodsSum + ndsRate
    private fun calculateNdsAmount(totalGoodsSum: BigDecimal): BigDecimal {
        val ndsPercentage = ndsRate.value!!.divide(BigDecimal("100.0"))
        return totalGoodsSum.multiply(ndsPercentage)
    }
    // This is the amount of NSP tax totalGoodsSum + nspRate
    private fun calculateNspAmount(totalGoodsSum: BigDecimal): BigDecimal {
        val ndsPercentage = nspRate.value!!.divide(BigDecimal("100.0"))
        return totalGoodsSum.multiply(ndsPercentage)
    }
    // This is a total sum of products with discount and charge added - sum(priceWithDiscount),
    // but no taxes
    private fun calculateTotalGoodsSum(): BigDecimal {
        val listOfProducts = productList.value ?: listOf()
        var totalGoodsSum = BigDecimal.ZERO
        for (product in listOfProducts) {
            totalGoodsSum += calculatePriceWithDiscount(product)
        }
        return totalGoodsSum
    }
    // This is the price with a discount and charge - price - discount + charge , (no taxes added)
    private fun calculatePriceWithDiscount(product: Product): BigDecimal {
        val subtotal = product.productUnitPrice.multiply(product.productQuantity)
        val hundred = BigDecimal("100.0")
        val discount = subtotal.multiply(product.discount).divide(hundred)
        val allowance = subtotal.multiply(product.charge).divide(hundred)
        return subtotal.subtract(discount).add(allowance)
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
            val response = sellRepository.fetchProductCategoryRemotely()
            if (response.isSuccessful) {
                sellRepository.saveCatalogToDatabase(response.body()!!.result)
            } else {
                logout()
            }
        }
    }

    private suspend fun logout() {
        val result = sellRepository.logout()
        event.postValue(UserLogout(result.resultCode))
    }

    override fun fetchProductCatalogLocally() {
        safeCall(Dispatchers.IO) {
            productCatalog = sellRepository.catalogFromLocal.asLiveData()
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

class UserLogout(val resultCode: String) : CoreEvent()