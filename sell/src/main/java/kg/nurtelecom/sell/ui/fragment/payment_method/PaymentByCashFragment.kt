package kg.nurtelecom.sell.ui.fragment.payment_method

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.FragmentPaymentByCashBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.core.CoreFragment
import java.math.BigDecimal

class PaymentByCashFragment : CoreFragment<FragmentPaymentByCashBinding>() {

    override val vm: SellMainViewModel by activityViewModels()

    override fun setupViews() {
        super.setupViews()
        vb.cwSum.apply {
            setCardTitle(R.string.sum_pay)
            setBackground(R.drawable.green_background)
        }
        vb.etReceived.apply {
            setTitle(R.string.et_received)
            setContentColor(R.color.colorBlack)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        val anotherTax = BigDecimal("1.01")
        vm.calculateTaxSum().observe(viewLifecycleOwner) {sum ->
            vb.cwSum.setCardContent(sum.multiply(anotherTax))
        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentByCashBinding = FragmentPaymentByCashBinding.inflate(inflater, container, false)

    companion object {
        fun newInstance() = PaymentByCardFragment()
    }
}