package kg.nurtelecom.sell.ui.fragment.sell

import android.view.LayoutInflater
import android.view.ViewGroup
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.data.enums.OperationType
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.core.ItemClickListener
import kg.nurtelecom.sell.databinding.SellFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.fragment.adapter.ProductAdapter
import kg.nurtelecom.sell.ui.fragment.add_product.AddProductFragment
import kg.nurtelecom.sell.ui.fragment.payment_method.PaymentMethodFragment

class SellFragment : CoreFragment<SellFragmentBinding, SellMainViewModel>(SellMainViewModel::class), ItemClickListener {

    private val productAdapter: ProductAdapter = ProductAdapter(this)

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
        setupRegime()
        setupNavigation()
    }

    override fun subscribeToLiveData() {
        vm.productList.observe(viewLifecycleOwner, { product ->
            productAdapter.productList = product
        })
        vm.taxSum.observe(viewLifecycleOwner) { taxSum ->
            vb.icSumPay.setContent(taxSum)
        }
        vm.isProductEmpty.observe(viewLifecycleOwner) { state ->
            vb.icSumPay.changeEditText(state)
        }
    }

    override fun <T> onItemClick(value: T) {
        vm.removeProduct(value as Int)
        productAdapter.notifyDataSetChanged()
    }

    private fun setupRegime() {
        vb.dvRegime.setupDialog(vm.isRegimeNonFiscal)
        vb.dvRegime.hideDialog()
    }

    private fun setupNavigation() {
        vb.icSumPay.setOnClickListener {
            parentActivity.replaceFragment<PaymentMethodFragment>(R.id.sell_container)
        }

        vb.btnAddProduct.setOnClickListener {
            parentActivity.replaceFragment<AddProductFragment>(R.id.sell_container)
        }
    }

    private fun startPrepay() {
        parentActivity.replaceFragment<PaymentMethodFragment>(R.id.sell_container, false)
    }

    companion object {
        fun newInstance(): SellFragment = SellFragment()
    }
}