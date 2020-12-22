package kg.nurtelecom.sell.ui.fragment.price_output

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
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

class PriceOutputFragment : CoreFragment<PriceOutputFragmentBinding>() {

    override val vm: SellMainViewModel by activityViewModels()

    override fun setupViews() {
        setupCustomEditText()
        vb.checkBtn.setOnClickListener {
            navigate()
        }
    }

    private fun sendProduct(product: Product) {
        vm.addNewProduct(product)
    }

    private fun setupCustomEditText() {
        vb.icProductPrice.fetchTextState {
            if (it != null) vb.checkBtn.isEnabled = it.isNotEmpty()
        }
    }

    private fun navigate() {
        val activity = activity as AppCompatActivity
        sendProduct(fetchProductData())
        activity.replaceFragment(SellFragment.newInstance())
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
        return product
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): PriceOutputFragmentBinding = PriceOutputFragmentBinding.inflate(inflater, container, false)

    companion object {
        fun newInstance() = PriceOutputFragment()
    }
}
