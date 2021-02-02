package kg.nurtelecom.sell.ui.fragment.payment_method

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.core.extension.enable
import kg.nurtelecom.core.extension.visible
import kg.nurtelecom.data.enums.OperationType
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.FragmentPaymentByCashBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.utils.roundUp
import java.math.BigDecimal

class PaymentByCashFragment : CoreFragment<FragmentPaymentByCashBinding>() {

    override val vm: SellMainViewModel by activityViewModels()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentByCashBinding = FragmentPaymentByCashBinding.inflate(inflater, container, false)

    override fun setupToolbar(): Int = R.string.payment_method

    override fun setupViews() {
        super.setupViews()

        setupPaymentMode()

        vb.btnContinue.setOnClickListener {
            navigateToPrintCheck()
        }

        vb.icReceived.apply {
            fetchTextState {
                if (it != null) {
                    vb.btnContinue.enable(it.isNotEmpty())
                    if (it.isNotEmpty()) {
                        vb.btnContinue.text = getString(R.string.btn_continue)
                    } else {
                        setupButtons()
                    }
                }
            }
        }
    }

    override fun subscribeToLiveData() {
        val anotherTax = BigDecimal("1.01")
        vm.taxSum.observe(viewLifecycleOwner) { sum ->
            vb.icSum.setContent(sum.multiply(anotherTax).roundUp())
        }
    }

    private fun setupButtons() {
        when (vm.operationType) {
            OperationType.SALE -> vb.btnContinue.text = getString(R.string.no_back_money)
            OperationType.POSTPAY -> vb.btnContinue.text = getString(R.string.text_no_deposit)
            OperationType.PREPAY -> vb.btnContinue.text = getString(R.string.btn_continue)
        }
    }

    private fun setupPaymentMode() {
        when (vm.operationType) {
            OperationType.PREPAY -> {
                vb.icSum.visible(false)
                vb.btnContinue.enable(false)
            }
        }
        setupButtons()
    }

    private fun navigateToPrintCheck() {
        // TO DO inflates the print check fragment
    }

    companion object {
        fun newInstance() = PaymentByCashFragment()
    }
}