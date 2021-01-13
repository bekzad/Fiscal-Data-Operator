package kg.nurtelecom.sell.ui.fragment.sell

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.sell.R
import androidx.lifecycle.observe
import kg.nurtelecom.sell.R
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.core.ProductItemClickListener
import kg.nurtelecom.sell.databinding.SellFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.custom.CustomDialog.Companion.FISCAL_REGIME
import kg.nurtelecom.sell.ui.fragment.adapter.ProductAdapter
import kg.nurtelecom.sell.ui.fragment.add_product.AddProductFragment
import kg.nurtelecom.sell.ui.fragment.payment_method.PaymentMethodFragment
import kg.nurtelecom.sell.utils.replaceFragment


class SellFragment : CoreFragment<SellFragmentBinding>(), ProductItemClickListener {

    private val productAdapter: ProductAdapter = ProductAdapter(this)

    override val vm: SellMainViewModel by activityViewModels()

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        SellFragmentBinding.inflate(inflater, container, false)

    override fun setupViews() {
        navigateToAddFragment()
        navigateToPaymentMethod()
        vb.productRv.adapter = productAdapter
        vb.addProductButton.setOnClickListener {
            replaceFragment(R.id.sell_container, AddProductFragment.newInstance(), true)
        }
        setupDialog()
    }

    private fun setupDialog() {
        vb.modeDialog.setupDialog(FISCAL_REGIME)
        vb.modeDialog.hideDialog()
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        vm.productList.observe(viewLifecycleOwner, { product ->
            productAdapter.productList = product
        })
        vm.taxSum.observe(viewLifecycleOwner) { taxSum ->
            vb.icSumPay.setContent(taxSum)
        }
    }

    private fun navigateToAddFragment() {
        vb.addProductButton.setOnClickListener {
            replaceFragment(R.id.sell_container, AddProductFragment.newInstance())
        }
    }

    override fun removeProduct(position: Int) {
        vm.removeProductFromList(position)
        productAdapter.notifyDataSetChanged()
    }

    private fun navigateToPaymentMethod() {
        vb.icSumPay.setOnClickListener {
            replaceFragment(R.id.sell_container, PaymentMethodFragment.newInstance())
        }
    }

    companion object {
        fun newInstance(): SellFragment = SellFragment()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).setToolbarTitle(R.string.text_sale)
    }

    companion object {
        fun newInstance() = SellFragment()
    }
}
