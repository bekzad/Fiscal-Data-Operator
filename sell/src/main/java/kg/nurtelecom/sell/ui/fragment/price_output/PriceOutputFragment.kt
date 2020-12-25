package kg.nurtelecom.sell.ui.fragment.price_output

import android.graphics.Color
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
        setupCustomEditText()
        /*vb.priceEditText. { text, _, _, _ ->
            vb.checkBtn.isEnabled = !text?.isEmpty()!!
        }*/
    }

    private fun sendProduct(product: Product) {
        if (product.price != null) vms.addNewProduct(product)
    }

    private fun setupCustomEditText() {
        vb.priceEditText.apply {
            setTitle(R.string.price)
            setTextColor(Color.BLACK)
            setBackground(R.drawable.white_background)
            isEditable(true)
        }
        vb.countEditText.apply {
            setTitle(R.string.count)
            setTextColor(Color.BLACK)
            setBackground(R.drawable.white_background)
        }
        vb.skidkaEditText.apply {
            setTitle(R.string.discount)
            setTextColor(Color.GREEN)
            setBackground(R.drawable.white_background)
        }
        vb.allowanceEditText.apply {
            setTitle(R.string.allowance)
            setTextColor(Color.RED)
            setBackground(R.drawable.white_background)
        }
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
        var product: Product
        val price = vb.priceEditText.fetchInputData() ?: return Product(0.0,0.0, 0.0)
        val count = vb.countEditText.fetchInputData() ?: 1.0
        val discount = vb.skidkaEditText.fetchInputData() ?: 0.0
        val allowance = vb.allowanceEditText.fetchInputData() ?: 0.0

        val totalPrice = price.times(count)
        val totalPriceWithDiscount = totalPrice - totalPrice * discount / 100.0 + totalPrice * allowance / 100.0

        product = Product(
            price = price,
            count = count,
            totalPrice = totalPriceWithDiscount,
            discount = discount,
            allowance = allowance
        )
        return product
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy!")
    }
}