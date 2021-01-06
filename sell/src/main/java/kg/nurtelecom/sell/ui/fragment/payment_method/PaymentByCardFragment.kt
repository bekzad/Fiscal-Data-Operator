package kg.nurtelecom.sell.ui.fragment.payment_method

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.FragmentPaymentByCardBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.core.CoreFragment


class PaymentByCardFragment : CoreFragment<FragmentPaymentByCardBinding>() {

    override val vm: SellMainViewModel by activityViewModels()

    override fun setupViews() {
        super.setupViews()

        vb.btnContinueCard.setOnClickListener {
            navigateToPrintCheck()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        vm.calculateTaxSum().observe(viewLifecycleOwner) {sum ->
            vb.icSumCard.setContent(sum)
            vb.icReceivedCard.setContent(sum)
        }
    }

    private fun navigateToPrintCheck() {
        // TO DO inflates the print check fragment
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentByCardBinding = FragmentPaymentByCardBinding.inflate(inflater, container, false)

    companion object {
        fun newInstance() = PaymentByCardFragment()
    }
}