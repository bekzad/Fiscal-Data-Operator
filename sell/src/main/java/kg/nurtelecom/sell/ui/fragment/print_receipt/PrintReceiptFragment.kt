package kg.nurtelecom.sell.ui.fragment.print_receipt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.FragmentPrintReceiptBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.fragment.sell.SellFragment
import kg.nurtelecom.sell.utils.isNotZero
import java.math.BigDecimal

class PrintReceiptFragment : CoreFragment<FragmentPrintReceiptBinding, SellMainViewModel>(SellMainViewModel::class) {

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPrintReceiptBinding = FragmentPrintReceiptBinding.inflate(inflater, container, false)

    override fun setupToolbar(): Int = R.string.print_check

    override fun setupViews() {
        vb.btnNewSale.setOnClickListener {
            navigateToSellFragment()
        }
    }

    override fun subscribeToLiveData() {
        vm.taxSum.observe(viewLifecycleOwner) { sum ->
            vb.tvPaymentAmount.text = getString(R.string.amount_in_soms, sum)
        }
        vm.change.observe(viewLifecycleOwner) { change ->
            if (change.isNotZero()) {
                vb.tvChangeText.visibility = View.VISIBLE
                vb.tvChangeAmount.text = getString(R.string.amount_in_soms, change)
            }
        }
    }

    private fun navigateToSellFragment() {
        clearVM()
        parentActivity.replaceFragment<SellFragment>(R.id.sell_container, false)
    }

    private fun clearVM() {
        vm.nspRate.value = BigDecimal.ZERO
        vm.productList.value = mutableListOf()
        vm.updateTaxSum()
        vm.isProductEmpty.value = true
    }

    companion object {
        fun newInstance() = PrintReceiptFragment()
    }

}