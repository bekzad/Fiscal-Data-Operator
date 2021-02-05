package kg.nurtelecom.sell.ui.activity

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.receipt.result.FetchReceiptResult
import kg.nurtelecom.data.sell.AllProducts
import kg.nurtelecom.data.sell.Product
import kg.nurtelecom.sell.repository.SellRepository
import kg.nurtelecom.sell.utils.roundUp
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import java.math.BigDecimal


abstract class SellMainViewModel : CoreViewModel() {

    abstract val productList: MutableLiveData<MutableList<Product>>
    abstract val taxSum: MutableLiveData<BigDecimal>
    abstract val selectedProductData: MutableLiveData<AllProducts>
    abstract val fetchReceiptResult: MutableLiveData<Response<FetchReceiptResult>>
    abstract val fetchReceiptResultString: MutableLiveData<Response<String>>

    abstract fun addNewProduct(product: Product)

    abstract fun removeProductFromList(position: Int)

    abstract fun sendSelectedProduct(product: AllProducts)

    abstract fun fetchReceipt(fetchReceiptRequest: String)

    // TODO: must be changed
    abstract val allProducts: MutableLiveData<MutableList<AllProducts>>

    open fun clearSelectedProduct() {}
}


class SellMainViewModelImpl(val repository: SellRepository) : SellMainViewModel() {

    override val productList: MutableLiveData<MutableList<Product>> =
        MutableLiveData(mutableListOf())

    override val taxSum: MutableLiveData<BigDecimal> = MutableLiveData(BigDecimal.ZERO)

    override val selectedProductData: MutableLiveData<AllProducts> = MutableLiveData()

    override val fetchReceiptResult: MutableLiveData<Response<FetchReceiptResult>> = MutableLiveData()
    override val fetchReceiptResultString: MutableLiveData<Response<String>> = MutableLiveData()

    override fun addNewProduct(product: Product) {
        productList.value?.add(product)
        taxSum.value = calculateTaxSum()
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

    override fun removeProductFromList(position: Int) {
        productList.value?.removeAt(position)
        taxSum.value = calculateTaxSum()
    }

    override fun sendSelectedProduct(product: AllProducts) {
        selectedProductData.value = product
    }

    override fun clearSelectedProduct() {
        selectedProductData.value = AllProducts("", BigDecimal.ZERO)
    }

    override fun fetchReceipt(fetchReceiptRequest: String) {
        safeCall(Dispatchers.IO) {
            val response = repository.fetchReceipt(fetchReceiptRequest)
            fetchReceiptResultString.postValue(response)

            val gson = Gson()
            val jsonBody = response.body() ?: ""
            val jsonString = """${jsonBody}"""
//
//            val fetchReceiptResult = gson.fromJson(jsonString, FetchReceiptResult::java)
//            fetchReceiptResult.postValue()
        }
    }

    // TODO: must be changed
    private val mockedAllProducts = mutableListOf(
        AllProducts("Test product name1", BigDecimal("25.00")),
        AllProducts("Test product name2", BigDecimal("45.00"))
    )

    override val allProducts: MutableLiveData<MutableList<AllProducts>> =
        MutableLiveData(mockedAllProducts)

}