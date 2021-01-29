package kg.nurtelecom.sell.ui.fragment.payment_method

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.data.enums.OperationType
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.FragmentPaymentByCardBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import java.math.BigDecimal


class PaymentByCardFragment : CoreFragment<FragmentPaymentByCardBinding>() {

    override val vm: SellMainViewModel by activityViewModels()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentByCardBinding = FragmentPaymentByCardBinding.inflate(inflater, container, false)

    override fun setupToolbar(): Int = R.string.payment_method

    override fun setupViews() {
        super.setupViews()

        setupButtons()

        vb.btnContinueCard.setOnClickListener {
            navigateToPrintCheck()
        }
    }

    override fun subscribeToLiveData() {
        vm.taxSum.observe(viewLifecycleOwner) { sum ->
            vb.icSumCard.setContent(sum)
        }
    }

    private fun setupButtons() {
        when (vm.operationType) {
            OperationType.SALE -> {
                vb.btnContinueCard.text = getString(R.string.btn_continue)
                vb.icReceivedCard.setContent(vm.taxSum.value ?: BigDecimal.ZERO)
            }
            OperationType.POSTPAY -> {
                setupCreditMode()
            }
        }
    }

    private fun setupCreditMode() {
        vb.btnContinueCard.text = getString(R.string.text_no_deposit)
        vb.icReceivedCard.setIsEditable(true)
        vb.icReceivedCard.eraseContent()
        vb.icReceivedCard.fetchTextState {
            if (!it.isNullOrEmpty()) {
                vb.btnContinueCard.text = getString(R.string.btn_continue)
            } else {
                vb.btnContinueCard.text = getString(R.string.text_no_deposit)
            }
        }
    }

    private fun navigateToPrintCheck() {
        // TO DO inflates the print check fragment
    }

    companion object {
        fun newInstance() = PaymentByCardFragment()
    }
}