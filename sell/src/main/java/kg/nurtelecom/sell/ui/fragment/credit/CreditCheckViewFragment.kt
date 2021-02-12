package kg.nurtelecom.sell.ui.fragment.credit

import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.CreditCheckViewFragmentBinding
import kg.nurtelecom.sell.ui.fragment.payment_method.PaymentMethodFragment
import kg.nurtelecom.sell.ui.fragment.refund.RefundFragment.Companion.CHECK_ID

class CreditCheckViewFragment : CoreFragment<CreditCheckViewFragmentBinding, CreditCheckViewFragmentVM>(CreditCheckViewFragmentVM::class) {

    override fun subscribeToLiveData() {
        vm.detailCreditCheckHistory.observe(viewLifecycleOwner) {
            parentActivity.setToolbarTitle(getString(R.string.cash_check_number, it.receipt.id))
            vb.textOne.setHint(it.taxSalesPointName)
            vb.textTwo.setHint(it.inn)
            vb.textThree.setHint(it.gnsRegNum.toString())
            vb.textFour.setHint(it.cashRegisterName)
            vb.textFive.setHint(it.cashRegisterVersion)
            vb.textSix.setHint(it.taxAccountingMethodName)
        }
    }
    override fun setupViews() {
        val someInt = requireArguments().getInt(CHECK_ID)
        vm.fetchDetailCheckHistory(someInt)
        vb.btnCloseCredit.setOnClickListener {
            parentActivity.replaceFragment<PaymentMethodFragment>(R.id.sell_container)
        }
    }
    override fun getBinding(): CreditCheckViewFragmentBinding {
        return CreditCheckViewFragmentBinding.inflate(layoutInflater)
    }
    companion object{
        fun newInstance() : CreditCheckViewFragment {
            return CreditCheckViewFragment()
        }
    }
}