package kg.nurtelecom.sell.ui.fragment.payment_method

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
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

        vb.btnContinue.setOnClickListener {
            navigateToPrintCheck()
        }

        vb.icReceived.apply {
            fetchTextState {
                if (it != null) vb.btnContinue.isEnabled = it.isNotEmpty()
            }
        }
    }

    override fun subscribeToLiveData() {
        val anotherTax = BigDecimal("1.01")
        vm.taxSum.observe(viewLifecycleOwner) { sum ->
            vb.icSum.setContent(sum.multiply(anotherTax).roundUp())
        }
    }

    private fun navigateToPrintCheck() {
        // TO DO inflates the print check fragment
    }

    companion object {
        fun newInstance() = PaymentByCashFragment()
    }
}