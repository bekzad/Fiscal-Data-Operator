package kg.nurtelecom.sell.ui.fragment.price_output

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.data.sell.Product
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.PriceOutputFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.core.CoreFragment
import kg.nurtelecom.data.sell.Product
import kg.nurtelecom.sell.ui.fragment.sell.SellFragment
import kg.nurtelecom.sell.utils.isZero
import kg.nurtelecom.sell.utils.replaceFragment
import java.math.BigDecimal
import kg.nurtelecom.sell.utils.addFragment
import kg.nurtelecom.sell.utils.parentActivity
import java.math.BigDecimal


class PriceOutputFragment : CoreFragment<PriceOutputFragmentBinding>() {

    override val vm: SellMainViewModel by activityViewModels()

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        PriceOutputFragmentBinding.inflate(inflater, container, false)

    override fun setupViews() {
        setupCustomEditText()
        vb.checkBtn.setOnClickListener { navigateToSellFragment() }
    }

    private fun sendProduct(product: Product) {
        vm.addNewProduct(product)
        vm.clearSelectedProductValue()
    }

    private fun setupCustomEditText() {
        vb.apply {
            icProductPrice.fetchTextState {
                if (it != null) vb.checkBtn.isEnabled = it.isNotEmpty()
            }
            icProductCount.fetchTextState {
                vb.checkBtn.isEnabled = it?.isNotEmpty() ?: true
            }
        }
    }

    private fun navigateToSellFragment() {
        sendProduct(fetchProductData())
        parentActivity.addFragment(SellFragment.newInstance(), true)
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        vm.selectedProductData.observe(viewLifecycleOwner, {
            vb.icProductPrice.setContent(it.productPrice)
        })
    }

    private fun fetchProductData(): Product {
        val product: Product
        val countCanBeZero = vb.icProductCount.fetchInputData()
        val count = if (countCanBeZero.isZero()) BigDecimal.ONE else countCanBeZero

        product = Product(
            price = vb.icProductPrice.fetchInputData(),
            count = count,
            discount = vb.icProductDiscount.fetchInputData(),
            allowance = vb.icProductAllowance.fetchInputData()
        )
    }

    companion object {
        fun newInstance() = PriceOutputFragment()
    }
}