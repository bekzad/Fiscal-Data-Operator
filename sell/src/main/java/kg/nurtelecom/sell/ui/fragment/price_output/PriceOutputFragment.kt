package kg.nurtelecom.sell.ui.fragment.price_output

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.INavigation
import kg.nurtelecom.sell.databinding.PriceOutputFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.fragment.adapter.Product
import kg.nurtelecom.sell.ui.fragment.sell.SellFragment
import java.math.BigDecimal

class PriceOutputFragment : Fragment()
    /*CoreFragment<PriceOutputFragmentBinding, SellMainViewModel>(SellMainViewModel::class)*/, INavigation {

    companion object {
        fun newInstance() = PriceOutputFragment()
    }

    init {
        println("PriceOutputFragment INIT!")
    }

    private lateinit var vb: PriceOutputFragmentBinding
    private val vms: SellMainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = PriceOutputFragmentBinding.inflate(inflater, container, false)
        setupViews()
        return vb.root
    }

    private fun setupViews() {
        vb.checkBtn.setOnClickListener {
            navigate()
        }
    }

    private fun sendProduct(product: Product) {
        if (product.price != null) vms.addNewProduct(product)
    }


    override fun navigate() {
        val activity = activity as AppCompatActivity
        sendProduct(fetchProductData())
        activity.supportFragmentManager
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

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy!")
    }
}