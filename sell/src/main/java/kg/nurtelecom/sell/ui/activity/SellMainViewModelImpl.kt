package kg.nurtelecom.sell.ui.activity

import androidx.lifecycle.MutableLiveData
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.sell.AllProducts
import kg.nurtelecom.data.sell.CatalogResult
import kg.nurtelecom.data.sell.Product
import kg.nurtelecom.data.sell.Products
import kg.nurtelecom.sell.repository.SellRepository
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
}


class SellMainViewModelImpl(private val repository: SellRepository) : SellMainViewModel() {

    override val productList: MutableLiveData<MutableList<Product>> =
        MutableLiveData(mutableListOf())

    override val taxSum: MutableLiveData<BigDecimal> = MutableLiveData(BigDecimal.ZERO)

    override val selectedProductData: MutableLiveData<AllProducts> = MutableLiveData()

    override var isProductEmpty: MutableLiveData<Boolean> = MutableLiveData(true)

    override val isRegimeNonFiscal: Boolean = repository.fetchRegime

    override val productCatalog: MutableLiveData<List<CatalogResult>> = MutableLiveData(listOf())

    init {
        fetchProductCatalog()
    }

    override fun addNewProduct(product: Product) {
        productList.value?.add(product)
        taxSum.value = calculateTaxSum()
        isProductEmpty.value = false
    }

    override fun fetchProductCatalog() {
        if (!repository.fetchRegime) {
            safeCall(Dispatchers.IO) {
                productCatalog.postValue(repository.fetchProductCategory())
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
}