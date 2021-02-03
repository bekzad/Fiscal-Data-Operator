package kg.nurtelecom.sell.ui.fragment.history.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.ChecksHistoryDetailFragmentBinding
import kg.nurtelecom.sell.ui.fragment.history.HistoryDetailsComponentView.Companion.CHECK_ID
import kg.nurtelecom.sell.ui.fragment.history.HistoryViewModel

class HistoryDetailFragment : CoreFragment<ChecksHistoryDetailFragmentBinding, HistoryViewModel>() {

    override fun setupViews() {
        val someInt = requireArguments().getInt(CHECK_ID)
        vm.fetchDetailCheckHistory(someInt)
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        vm.detailCheckHistory.observe(viewLifecycleOwner) {
            parentActivity.setToolbarTitle(getString(R.string.cash_check_number, it.receipt.id))
            vb.fieldOne.setHint(it.taxSalesPointName)
            vb.fieldTwo.setHint(it.inn)
            vb.fieldThree.setHint(it.gnsRegNum.toString())
            vb.fieldFour.setHint(it.cashRegisterName)
            vb.fieldFive.setHint(it.cashRegisterVersion)
            vb.fieldSix.setHint(it.taxAccountingMethodName)
        }
    }

    companion object {
        fun newInstance(): HistoryDetailFragment {
            return HistoryDetailFragment()
        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ChecksHistoryDetailFragmentBinding {
        return ChecksHistoryDetailFragmentBinding.inflate(layoutInflater)
    }

    override fun setupToolbar(): Int = R.string.history_title
}