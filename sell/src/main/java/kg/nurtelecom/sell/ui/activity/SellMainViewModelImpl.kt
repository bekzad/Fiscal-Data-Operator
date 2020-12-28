package kg.nurtelecom.sell.ui.activity

import androidx.lifecycle.MutableLiveData
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.sell.Product
import kg.nurtelecom.sell.utils.roundUp
import java.math.BigDecimal
import java.math.RoundingMode
import kg.nurtelecom.data.sell.AllProducts
import kg.nurtelecom.data.sell.Product


abstract class SellMainViewModel : CoreViewModel() {

    abstract val productList: MutableLiveData<MutableList<Product>>
    abstract val taxSum: MutableLiveData<Double>
    abstract val selectProductData: MutableLiveData<AllProducts>

    abstract fun addNewProduct(product: Product)

    abstract fun removeProductFromList(position: Int)

    abstract fun sendSelectedProduct(product: AllProducts)

    // TODO: must be changed
    abstract val allProducts: MutableLiveData<MutableList<AllProducts>>
}


class SellMainViewModelImpl : SellMainViewModel() {

    override val productList: MutableLiveData<MutableList<Product>> =
        MutableLiveData(mutableListOf())

    override val taxSum: MutableLiveData<Double> = MutableLiveData(0.0)

    override val selectProductData: MutableLiveData<AllProducts> = MutableLiveData()

    override fun addNewProduct(product: Product) {
        productList.value?.add(product)
        taxSum.value = calculateTaxSum()
    }

    fun calculateTaxSum(): LiveData<BigDecimal> {
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

        return MutableLiveData(taxSum.roundUp())
    }

    override fun removeProductFromList(position: Int) {
        productList.value?.removeAt(position)
        taxSum.value = calculateTaxSum()
    }

    override fun sendSelectedProduct(product: AllProducts) {
        selectProductData.value = product
    }

    // TODO: must be changed
    private val mockedAllProducts = mutableListOf(
        AllProducts("Test product name1", 25.00),
        AllProducts("Test product name2", 30.00)
    )
    override val allProducts: MutableLiveData<MutableList<AllProducts>> =
        MutableLiveData(mockedAllProducts)

}