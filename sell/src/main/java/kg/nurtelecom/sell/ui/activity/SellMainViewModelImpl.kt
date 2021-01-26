package kg.nurtelecom.sell.ui.activity

import androidx.lifecycle.MutableLiveData
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.sell.AllProducts
import kg.nurtelecom.data.sell.Product
import kg.nurtelecom.sell.repository.SellRepository
import kg.nurtelecom.sell.utils.roundUp
import java.math.BigDecimal


abstract class SellMainViewModel : CoreViewModel() {

    abstract val productList: MutableLiveData<MutableList<Product>>
    abstract val taxSum: MutableLiveData<BigDecimal>
    abstract val selectedProductData: MutableLiveData<AllProducts>
    abstract var isProductEmpty: MutableLiveData<Boolean>

    open val regimeState: Boolean = false

    abstract fun addNewProduct(product: Product)

    abstract fun removeProductFromList(position: Int)

    abstract fun sendSelectedProduct(product: AllProducts)

    // TODO: must be changed
    abstract val allProducts: MutableLiveData<MutableList<AllProducts>>

    abstract fun clearSelectedProduct()
}


class SellMainViewModelImpl(private val repository: SellRepository) : SellMainViewModel() {

    override val productList: MutableLiveData<MutableList<Product>> =
        MutableLiveData(mutableListOf())

    override val taxSum: MutableLiveData<BigDecimal> = MutableLiveData(BigDecimal.ZERO)

    override val selectedProductData: MutableLiveData<AllProducts> = MutableLiveData()

    override var isProductEmpty: MutableLiveData<Boolean> = MutableLiveData(true)

    override val regimeState: Boolean
        get() = repository.fetchRegime()

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
            totalPrice = product.totalPrice
            tax = (totalPrice.multiply(taxRate).divide(hundred))
            taxSum = taxSum.add(totalPrice).add(tax)
        }

        return taxSum.roundUp()
    }

    override fun removeProductFromList(position: Int) {
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

    // TODO: must be changed
    private val mockedAllProducts = mutableListOf(
        AllProducts("Мыло со вкусом солнца", BigDecimal("25.00")),
        AllProducts("Воздух без воздуха", BigDecimal("45.00"))
    )

    override val allProducts: MutableLiveData<MutableList<AllProducts>> =
        MutableLiveData(mockedAllProducts)

}