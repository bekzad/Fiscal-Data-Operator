package kg.nurtelecom.sell.ui.fragment.sell

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.sell.R
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

    override fun setupToolbar(): Int = R.string.text_sale

    override fun setupViews() {
        vb.productRv.adapter = productAdapter
        setupDialog()
        setupNavigation()
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

    override fun removeProduct(position: Int) {
        vm.removeProductFromList(position)
        productAdapter.notifyDataSetChanged()
    }

    private fun setupDialog() {
        vb.modeDialog.setupDialog(FISCAL_REGIME)
        vb.modeDialog.hideDialog()
    }

    private fun setupNavigation() {
        vb.icSumPay.setOnClickListener {
            replaceFragment(R.id.sell_container, PaymentMethodFragment.newInstance(), true)
        }

        vb.addProductButton.setOnClickListener {
            replaceFragment(R.id.sell_container, AddProductFragment.newInstance(), true)
        }
    }

    companion object {
        fun newInstance() = SellFragment()
    }
}
