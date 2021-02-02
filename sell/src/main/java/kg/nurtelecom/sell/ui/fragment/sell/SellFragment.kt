package kg.nurtelecom.sell.ui.fragment.sell

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.data.enums.OperationType
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.core.ProductItemClickListener
import kg.nurtelecom.sell.databinding.SellFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.custom.CustomDialog.Companion.FISCAL_REGIME
import kg.nurtelecom.sell.ui.fragment.adapter.ProductAdapter
import kg.nurtelecom.sell.ui.fragment.add_product.AddProductFragment
import kg.nurtelecom.sell.ui.fragment.payment_method.PaymentMethodFragment

class SellFragment : CoreFragment<SellFragmentBinding>(), ProductItemClickListener {

    private val productAdapter: ProductAdapter = ProductAdapter(this)

    override val vm: SellMainViewModel by activityViewModels()

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        SellFragmentBinding.inflate(inflater, container, false)

    override fun setupToolbar(): Int {
        return when (vm.operationType) {
            OperationType.SALE -> R.string.text_sale
            OperationType.POSTPAY -> R.string.text_credit
            OperationType.PREPAY -> {
                startPrepay()
                R.string.prepay
            }
            else -> R.string.text_sale
        }
    }

    override fun setupViews() {
        vb.rvProduct.adapter = productAdapter
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
            parentActivity.replaceFragment<PaymentMethodFragment>(R.id.sell_container, true)
        }

        vb.btnAddProduct.setOnClickListener {
            parentActivity.replaceFragment<AddProductFragment>(R.id.sell_container, true)
        }
    }

    private fun startPrepay() {
        parentActivity.replaceFragment<PaymentMethodFragment>(R.id.sell_container)
    }

    companion object {
        fun newInstance() = SellFragment()
    }
}