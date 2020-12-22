package kg.nurtelecom.sell.ui.fragment.sell

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.SellFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.core.CoreFragment
import kg.nurtelecom.data.sell.Product
import kg.nurtelecom.sell.ui.fragment.adapter.ProductAdapter
import kg.nurtelecom.sell.ui.fragment.add_product.AddProductFragment
import kg.nurtelecom.sell.ui.fragment.payment_method.PaymentMethodFragment
import kg.nurtelecom.sell.utils.replaceFragment

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
        navigateToAddFragment()
        navigateToPaymentMethod()
        subscribeToLiveData()
    }

    private fun setupTaxView() {
        vm.calculateTaxSum().observe(viewLifecycleOwner) {
            vb.icSumPay.setContent(it)
        }
    }

    private fun subscribeToLiveData() {
        vm.productList.observe(viewLifecycleOwner, { product ->
            setupRV(product)
        })
    }

    private fun navigateToAddFragment() {
        vb.addProductButton.setOnClickListener {
            val activity = activity as AppCompatActivity
            activity.replaceFragment(AddProductFragment.newInstance())
        }
    }

    private fun navigateToPaymentMethod() {
        vb.icSumPay.setOnClickListener {
            val activity = activity as AppCompatActivity
            activity.replaceFragment(PaymentMethodFragment.newInstance())
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
