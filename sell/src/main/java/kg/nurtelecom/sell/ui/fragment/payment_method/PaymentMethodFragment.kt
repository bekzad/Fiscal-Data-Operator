package kg.nurtelecom.sell.ui.fragment.payment_method

import android.view.LayoutInflater
import android.view.ViewGroup
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.core.extension.visible
import kg.nurtelecom.data.enums.OperationType
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.FragmentPaymentMethodBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import java.math.BigDecimal


class PaymentMethodFragment : CoreFragment<FragmentPaymentMethodBinding, SellMainViewModel>(SellMainViewModel::class) {

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentMethodBinding {
        return FragmentPaymentMethodBinding.inflate(inflater, container, false)
    }

    override fun setupToolbar(): Int = R.string.payment_method

    override fun setupViews() {
        setupNavigate()
        setupPaymentMode()
    }

    override fun subscribeToLiveData() {
        vm.taxSum.observe(viewLifecycleOwner) { sum ->
            vb.tvPaymentAmount.text = getString(R.string.payment_amount, sum)
        }
    }

    private fun setupNavigate() {
        vb.btnCashPayment.setOnClickListener {
            vm.nspRate.value = BigDecimal.ONE
            vm.updateTaxSum()
            parentActivity.replaceFragment<PaymentByCashFragment>(R.id.sell_container, false)
        }

        vb.btnCardPayment.setOnClickListener {
            vm.nspRate.value = BigDecimal.ZERO
            vm.updateTaxSum()
            parentActivity.replaceFragment<PaymentByCardFragment>(R.id.sell_container, false)
        }
    }

    private fun setupPaymentMode() {
        when (vm.operationType) {
            OperationType.PREPAY -> {
                vb.tvPaymentAmount.visible(false)
                vb.tvToBePaid.visible(false)
            }
        }
    }

    companion object {
        fun newInstance() = PaymentMethodFragment()
    }
}