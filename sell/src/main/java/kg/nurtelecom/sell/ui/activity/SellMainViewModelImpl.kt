package kg.nurtelecom.sell.ui.activity

import androidx.lifecycle.MutableLiveData
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.sell.Product
import kg.nurtelecom.sell.utils.roundUp
import java.math.BigDecimal
import java.math.RoundingMode


abstract class SellMainViewModel : CoreViewModel() {

    abstract val productList: MutableLiveData<MutableList<Product>>
    abstract val taxSum: MutableLiveData<Double>

    abstract fun addNewProduct(product: Product)

    abstract fun removeProductFromList(position: Int)
}


class SellMainViewModelImpl : SellMainViewModel() {

    override val productList: MutableLiveData<MutableList<Product>> = MutableLiveData(mutableListOf())
    override val taxSum: MutableLiveData<Double> = MutableLiveData(0.0)

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

    override fun removeProductFromList(position : Int) {
        productList.value?.removeAt(position)
        taxSum.value = calculateTaxSum()
    }
}