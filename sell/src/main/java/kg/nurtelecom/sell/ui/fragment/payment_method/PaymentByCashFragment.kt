package kg.nurtelecom.sell.ui.fragment.payment_method

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.sell.databinding.FragmentPaymentByCashBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.core.CoreFragment
import java.math.BigDecimal
import java.math.RoundingMode

class PaymentByCashFragment : CoreFragment<FragmentPaymentByCashBinding>() {

    override val vm: SellMainViewModel by activityViewModels()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        val anotherTax = BigDecimal("1.01")
        vm.calculateTaxSum().observe(viewLifecycleOwner) {sum ->
            vb.icSum.setContent(sum.multiply(anotherTax).setScale(2, RoundingMode.CEILING))
        }
    }

    private fun navigateToPrintCheck() {
        // TO DO inflates the print check fragment
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentByCashBinding = FragmentPaymentByCashBinding.inflate(inflater, container, false)

    companion object {
        fun newInstance() = PaymentByCashFragment()
    }
}