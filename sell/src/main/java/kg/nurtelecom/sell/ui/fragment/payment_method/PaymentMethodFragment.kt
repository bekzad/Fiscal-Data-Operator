package kg.nurtelecom.sell.ui.fragment.payment_method

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.FragmentPaymentMethodBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.core.CoreFragment


class PaymentMethodFragment : CoreFragment<FragmentPaymentMethodBinding>() {

    override val vm: SellMainViewModel by activityViewModels()

    override fun setupViews() {
        navigate()
    }

    private fun navigate() {
        vb.btnCashPayment.setOnClickListener {
            val activity = activity as AppCompatActivity
            activity
                .supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, PaymentByCashFragment.newInstance())
                .commit()
        }

        vb.btnCardPayment.setOnClickListener {
            val activity = activity as AppCompatActivity
            activity
                .supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, PaymentByCardFragment.newInstance())
                .commit()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        vm.calculateTaxSum().observe(viewLifecycleOwner) {sum ->
            vb.twPaymentAmount.text = sum.toString()
        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentMethodBinding = FragmentPaymentMethodBinding.inflate(inflater, container, false)

    companion object {
        fun newInstance() = PaymentMethodFragment()
    }
}