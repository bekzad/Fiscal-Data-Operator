package kg.nurtelecom.sell.ui.fragment.payment_method

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.FragmentPaymentMethodBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel


class PaymentMethodFragment : CoreFragment<FragmentPaymentMethodBinding>() {

    override val vm: SellMainViewModel by activityViewModels()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentMethodBinding {
        return FragmentPaymentMethodBinding.inflate(inflater, container, false)
    }

    override fun setupToolbar(): Int = R.string.payment_method

    override fun setupViews() {
        setupNavigate()
    }

    override fun subscribeToLiveData() {
        vm.taxSum.observe(viewLifecycleOwner) { sum ->
            vb.twPaymentAmount.text = sum.toString()
        }
    }

    private fun setupNavigate() {
        vb.btnCashPayment.setOnClickListener {
            parentActivity.replaceFragment<PaymentByCashFragment>(R.id.sell_container, true)
        }

        vb.btnCardPayment.setOnClickListener {
            parentActivity.replaceFragment<PaymentByCardFragment>(R.id.sell_container, true)
        }
    }

    companion object {
        fun newInstance() = PaymentMethodFragment()
    }
}