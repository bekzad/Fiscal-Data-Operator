package kg.nurtelecom.sell.ui.fragment.price_output

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.data.sell.Product
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.PriceOutputFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.core.CoreFragment
import kg.nurtelecom.data.sell.Product
import kg.nurtelecom.sell.ui.fragment.sell.SellFragment
import kg.nurtelecom.sell.utils.isZero
import kg.nurtelecom.sell.utils.replaceFragment
import java.math.BigDecimal
import kg.nurtelecom.sell.utils.parentActivity
import kg.nurtelecom.sell.utils.replaceFragment


class PriceOutputFragment : CoreFragment<PriceOutputFragmentBinding>() {

    override val vm: SellMainViewModel by activityViewModels()

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        PriceOutputFragmentBinding.inflate(inflater, container, false)

    override fun setupViews() {
        setupCustomEditText()
        subscribeSelectedProduct()
        vb.checkBtn.setOnClickListener { navigateToSellFragment() }
    }

    private fun sendProduct(product: Product) {
        vm.addNewProduct(product)
    }

    private fun setupCustomEditText() {
        vb.icProductPrice.fetchTextState {
            if (it != null) vb.checkBtn.isEnabled = it.isNotEmpty()
        }
    }

    private fun navigateToSellFragment() {
        sendProduct(fetchProductData())
        activity.replaceFragment(SellFragment.newInstance())
    }

    private fun subscribeSelectedProduct() {
        vm.selectProductData.observe(viewLifecycleOwner, {
            vb.productPriceEt.setText(it.productPrice.toString())
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
