package kg.nurtelecom.sell.ui.fragment.sell

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.sell.R
import androidx.lifecycle.observe
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.core.ProductItemClickListener
import kg.nurtelecom.sell.databinding.SellFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.core.CoreFragment
import kg.nurtelecom.data.sell.Product
import kg.nurtelecom.sell.ui.custom.CustomDialog.Companion.FISCAL_REGIME
import kg.nurtelecom.sell.ui.custom.CustomDialog.Companion.NON_FISCAL_REGIME
import kg.nurtelecom.sell.ui.fragment.adapter.ProductAdapter
import kg.nurtelecom.sell.ui.fragment.add_product.AddProductFragment
import kg.nurtelecom.sell.ui.fragment.payment_method.PaymentMethodFragment
import kg.nurtelecom.sell.utils.replaceFragment


class SellFragment : CoreFragment<SellFragmentBinding>(), ProductItemClickListener {

    private lateinit var productAdapter: ProductAdapter

    override val vm: SellMainViewModel by activityViewModels()

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        SellFragmentBinding.inflate(inflater, container, false)

    private fun setupRV() {
        vb.productRv.apply {
            productAdapter = ProductAdapter(this@SellFragment)
            adapter = productAdapter
        }
    }

    override fun setupViews() {
        setupRV()
        setupTaxView()
        navigateToAddFragment()
        navigateToPaymentMethod()
        subscribeToLiveData()
        setupDialog()
    }

    private fun setupDialog() {
        vb.modeDialog.setupDialog(FISCAL_REGIME)
        vb.modeDialog.hideDialog()
    }

    // TODO: must be changed
    private fun setupTaxView() {
        vm.calculateTaxSum().observe(viewLifecycleOwner) {
            vb.icSumPay.setContent(it)
        }
    }

    private fun subscribeToLiveData() {
        vm.productList.observe(viewLifecycleOwner, { product ->
            productAdapter.productList = product
        })
        vm.taxSum.observe(viewLifecycleOwner) {
            vb.sumPayCv.setCardContent(it)
        }
    }

    private fun navigateToAddFragment() {
        vb.addProductButton.setOnClickListener {
            val activity = activity as AppCompatActivity
            activity.replaceFragment(AddProductFragment.newInstance())
        }
    }

    override fun removeItem(position: Int) {
        vm.removeProductFromList(position)
        productAdapter.notifyDataSetChanged()
    }

    private fun navigateToPaymentMethod() {
        vb.icSumPay.setOnClickListener {
            val activity = activity as AppCompatActivity
            activity.replaceFragment(PaymentMethodFragment.newInstance())
        }
        vb.sumPayCv.navigate {
            // navigate to PayMethodFragment
        }
    }

    companion object {
        fun newInstance() = SellFragment()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).setToolbarTitle(R.string.text_sale)
    }

    companion object {
        fun newInstance() = SellFragment()
    }
}
