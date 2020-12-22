package kg.nurtelecom.sell.ui.fragment.sell

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.SellFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.core.CoreFragment
import kg.nurtelecom.sell.ui.fragment.adapter.Product
import kg.nurtelecom.sell.ui.fragment.adapter.ProductAdapter
import kg.nurtelecom.sell.ui.fragment.add_product.AddProductFragment
import kg.nurtelecom.sell.ui.fragment.payment_method.PaymentMethodFragment

class SellFragment :
    CoreFragment<SellFragmentBinding>() {

    private lateinit var productAdapter: ProductAdapter

    override val vm: SellMainViewModel by activityViewModels()

    private fun setupRV(product: List<Product>) {
        vb.productRv.apply {
            productAdapter = ProductAdapter(product)
            adapter = productAdapter
        }
    }

    override fun setupViews() {
        setupRV(listOf())
        setupTaxView()
        navigate()
        navigateToPaymentMethod()
        observeProduct()
    }

    private fun setupTaxView() {
        vm.calculateTaxSum().observe(viewLifecycleOwner) {
            vb.icSumPay.setContent(it)
        }
    }

    private fun observeProduct() {
        vm.productList.observe(viewLifecycleOwner, { product ->
            setupRV(product)
        })
    }

    private fun navigate() {
        vb.addProductButton.setOnClickListener {
            val activity = activity as AppCompatActivity
            activity
                .supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, AddProductFragment.newInstance())
                .commit()
        }
    }

    private fun navigateToPaymentMethod() {
        vb.icSumPay.setOnClickListener {
            val activity = activity as AppCompatActivity
            activity
                .supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, PaymentMethodFragment.newInstance())
                .commit()
        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): SellFragmentBinding {
        return SellFragmentBinding.inflate(inflater, container, false)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).setToolbarTitle(R.string.text_sale)
    }

    companion object {
        fun newInstance() = SellFragment()
    }
}
