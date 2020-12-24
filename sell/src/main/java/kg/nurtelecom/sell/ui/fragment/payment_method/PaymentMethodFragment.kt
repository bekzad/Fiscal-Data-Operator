package kg.nurtelecom.sell.ui.fragment.payment_method

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.sell.databinding.FragmentPaymentMethodBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.core.CoreFragment


class PaymentMethodFragment : CoreFragment<FragmentPaymentMethodBinding>() {

    override val vm: SellMainViewModel by activityViewModels()

    override fun setupViews() {
        super.setupViews()
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentMethodBinding = FragmentPaymentMethodBinding.inflate(inflater, container, false)

    companion object {
        fun newInstance() = PaymentMethodFragment()
    }
}