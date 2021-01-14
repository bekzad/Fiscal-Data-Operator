package kg.nurtelecom.sell.ui.fragment.payment_method

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.FragmentPaymentMethodBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.utils.replaceFragment


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
            replaceFragment(R.id.sell_container, PaymentByCashFragment.newInstance(), true)
        }

        vb.btnCardPayment.setOnClickListener {
            replaceFragment(R.id.sell_container, PaymentByCardFragment.newInstance(), true)
        }
    }

    companion object {
        fun newInstance() = PaymentMethodFragment()
    }
}