package kg.nurtelecom.sell.ui.fragment.price_output

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.data.sell.Product
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.PriceOutputFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.fragment.sell.SellFragment
import kg.nurtelecom.sell.utils.isZero
import java.math.BigDecimal


class PriceOutputFragment : CoreFragment<PriceOutputFragmentBinding>() {

    override val vm: SellMainViewModel by activityViewModels()

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        PriceOutputFragmentBinding.inflate(inflater, container, false)

    override fun setupToolbar(): Int = R.string.price_entry

    override fun setupViews() {
        setupCustomEditText()
        vb.checkBtn.setOnClickListener { navigateToSellFragment() }
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        vm.selectedProductData.observe(viewLifecycleOwner, {
            vb.icProductPrice.setContent(it.productPrice)
        })
    }

    private fun sendProduct(product: Product) {
        vm.addNewProduct(product)
        vm.clearSelectedProduct()
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
        parentActivity.replaceFragment<SellFragment>(R.id.sell_container, true)
    }

    private fun fetchProductData(): Product {
        val countCanBeZero = vb.icProductCount.fetchInputData()
        val count = if (countCanBeZero.isZero()) BigDecimal.ONE else countCanBeZero

        return Product(
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