package kg.nurtelecom.sell.ui.fragment.sell

import android.view.LayoutInflater
import android.view.ViewGroup
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.core.extension.visible
import kg.nurtelecom.data.enums.OperationType
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.core.ProductItemClickListener
import kg.nurtelecom.sell.databinding.SellFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.fragment.adapter.ProductAdapter
import kg.nurtelecom.sell.ui.fragment.add_product.AddProductFragment
import kg.nurtelecom.sell.ui.fragment.payment_method.PaymentMethodFragment

class SellFragment : CoreFragment<SellFragmentBinding, SellMainViewModel>(SellMainViewModel::class),
    ProductItemClickListener {

    private val productAdapter: ProductAdapter = ProductAdapter(this)

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        SellFragmentBinding.inflate(inflater, container, false)

    override fun setupToolbar(): Int {
        return when (vm.operationType) {
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
        vm.isDialogShow.observe(viewLifecycleOwner, { state ->
            vb.dvRegime.visible(!state)
        })
    }

    override fun removeProduct(position: Int) {
        vm.removeProduct(position)
        productAdapter.notifyDataSetChanged()
    }

    private fun setupRegime() {
        vb.apply {
            dvRegime.setupDialog(vm.isRegimeNonFiscal)
            dvRegime.hideDialog()
        }
    }

    private fun setupNavigation() {
        vb.apply {
            icSumPay.setOnClickListener {
                parentActivity.replaceFragment<PaymentMethodFragment>(R.id.sell_container)
            }
            btnAddProduct.setOnClickListener {
                parentActivity.replaceFragment<AddProductFragment>(R.id.sell_container)
            }
        }
    }

    private fun startPrepay() {
        parentActivity.replaceFragment<PaymentMethodFragment>(R.id.sell_container, false)
    }

    override fun onStop() {
        super.onStop()
        vm.setDialogVisibility(true)
    }

    companion object {
        fun newInstance(): SellFragment = SellFragment()
    }
}