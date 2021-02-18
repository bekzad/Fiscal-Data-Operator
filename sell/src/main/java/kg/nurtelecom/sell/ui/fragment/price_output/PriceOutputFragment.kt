package kg.nurtelecom.sell.ui.fragment.price_output

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.data.sell.Product
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.PriceOutputFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.fragment.sell.SellFragment
import kg.nurtelecom.sell.utils.hideKeyboard
import kg.nurtelecom.sell.utils.isZero
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal


class PriceOutputFragment :
    CoreFragment<PriceOutputFragmentBinding, SellMainViewModel>(SellMainViewModel::class) {

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        PriceOutputFragmentBinding.inflate(inflater, container, false)

    override fun setupToolbar(): Int = R.string.price_entry

    override fun setupViews() {
        setupEditText()
        vb.btnCheck.setOnClickListener { navigateToSellFragment() }
        userInputValidation()
    }

    override fun subscribeToLiveData() {
        vm.selectedProductData.observe(this, { product ->
            if (vm.selectedProductData.value != null) {
                vb.tvName.text = product.name
                vb.icProductPrice.setContent(product.price)
            }
        })
    }

    private fun sendProduct(product: Product) {
        vm.addNewProduct(product)
        vm.clearSelectedProduct()
    }

    private fun setupEditText() {
        vb.apply {
            icProductPrice.fetchTextState {
                vm.setProductPrice(it.toString())
            }
            icProductDiscount.fetchTextState {
                vm.setProductCharge(it.toString())
            }
        }
    }

    private fun navigateToSellFragment() {
        sendProduct(fetchProductData())
        parentActivity.replaceFragment<SellFragment>(R.id.sell_container, true)
        parentActivity.hideKeyboard()
    }

    private fun fetchProductData(): Product {
        val countCanBeZero = vb.icProductCount.fetchInputData()
        val count = if (countCanBeZero.isZero()) BigDecimal.ONE else countCanBeZero

        var currentProductId: Long? = null
        var productName = ""
        vm.selectedProductData.value?.let { product ->
            currentProductId = product.id
            productName = product.name
        }

        return Product(
            productId = currentProductId,
            productName = productName,
            productUnitPrice = vb.icProductPrice.fetchInputData(),
            productQuantity = count,
            discount = vb.icProductDiscount.fetchInputData(),
            charge = vb.icProductAllowance.fetchInputData(),
            itemIndex = 0L
        )
    }

    private fun userInputValidation() {
        lifecycleScope.launch {
            whenStarted {
                vm.isSubmitBtnEnabled.collect { value ->
                    vb.btnCheck.isEnabled = value
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        vm.clearSelectedProduct()
    }

    companion object {
        fun newInstance() = PriceOutputFragment()
    }
}