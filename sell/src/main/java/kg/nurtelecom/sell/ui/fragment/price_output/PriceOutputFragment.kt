package kg.nurtelecom.sell.ui.fragment.price_output

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.PriceOutputFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.core.CoreFragment
import kg.nurtelecom.sell.ui.fragment.adapter.Product
import kg.nurtelecom.sell.ui.fragment.sell.SellFragment
import java.math.BigDecimal

class PriceOutputFragment : CoreFragment<PriceOutputFragmentBinding>() {

    override val vm: SellMainViewModel by activityViewModels()

    override fun setupViews() {
        vb.checkBtn.setOnClickListener {
            navigate()
        }
    }

    private fun sendProduct(product: Product) {
        vm.addNewProduct(product)
    }

    private fun setupCustomEditText() {
        vb.icProductPrice.apply {
            fetchTextState {
                vb.checkBtn.isEnabled = !it?.isEmpty()!!
            }
        }
    }

    private fun navigate() {
        val activity = activity as AppCompatActivity
        sendProduct(fetchProductData())
        activity
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, SellFragment.newInstance())
            .commit()
    }

    private fun fetchProductData(): Product {
        val product: Product
        val zero = BigDecimal.ZERO
        val price = vb.icProductPrice.fetchInputData() ?: return Product(null, zero, zero)
        val count = vb.icProductCount.fetchInputData() ?: BigDecimal.ONE
        val discountPercentage = vb.icProductDiscount.fetchInputData() ?: zero
        val allowancePercentage = vb.icProductAllowance.fetchInputData() ?: zero

        val totalPrice = price.multiply(count)
        val hundred = BigDecimal("100.0")
        val discount = totalPrice.multiply(discountPercentage).divide(hundred)
        val allowance = totalPrice.multiply(allowancePercentage).divide(hundred)
        val totalPriceWithDiscount = totalPrice.subtract(discount).add(allowance)

        product = Product(
            price = price,
            count = count,
            totalPrice = totalPriceWithDiscount,
            discount = discountPercentage,
            allowance = allowancePercentage
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
