package kg.nurtelecom.sell.ui.fragment.payment_method

import android.view.LayoutInflater
import android.view.ViewGroup
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.FragmentPaymentByCardBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel


class PaymentByCardFragment : CoreFragment<FragmentPaymentByCardBinding, SellMainViewModel>(SellMainViewModel::class) {


    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentByCardBinding = FragmentPaymentByCardBinding.inflate(inflater, container, false)

    override fun setupToolbar(): Int = R.string.payment_method

    override fun setupViews() {
        super.setupViews()

        vb.btnContinueCard.setOnClickListener {
            navigateToPrintCheck()
        }
    }

    override fun subscribeToLiveData() {
        vm.taxSum.observe(viewLifecycleOwner) { sum ->
            vb.icSumCard.setContent(sum)
            vb.icReceivedCard.setContent(sum)
        }
    }

    private fun navigateToPrintCheck() {
        // TO DO inflates the print check fragment
    }

    companion object {
        fun newInstance() = PaymentByCardFragment()
    }
}