package kg.nurtelecom.sell.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.sell.ui.fragment.adapter.Product

class SellMainViewModel : CoreViewModel() {

    init {
        println("SellMainViewModel INIT! ")
    }

    val productList: MutableLiveData<MutableList<Product>> = MutableLiveData(mutableListOf())

    fun addNewProduct(product: Product) {
        productList.value?.add(product)
        calculateTaxSum()
    }

    fun calculateTaxSum(): LiveData<Double> {
        var taxSum = 0.0
        for (i in productList.value ?: listOf()) {
            val totalPrice = i.totalPrice
            val tax = (totalPrice * (12.0 / 100))
            taxSum += (totalPrice + tax)
        }
        println("TAXSUM IS: $taxSum")
        return MutableLiveData(taxSum)
    }
}