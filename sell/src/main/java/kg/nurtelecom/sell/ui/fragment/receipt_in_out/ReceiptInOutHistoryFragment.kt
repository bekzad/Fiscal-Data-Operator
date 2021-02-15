package kg.nurtelecom.sell.ui.fragment.receipt_in_out

import android.view.LayoutInflater
import android.view.ViewGroup
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.FragmentReceiptInOutHistoryBinding

class ReceiptInOutHistoryFragment : CoreFragment<FragmentReceiptInOutHistoryBinding, ReceiptInOutHistoryVM>(ReceiptInOutHistoryVM::class) {

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentReceiptInOutHistoryBinding {
        return FragmentReceiptInOutHistoryBinding.inflate(layoutInflater)
    }

    override fun setupToolbar(): Int {
        return R.string.receipt_in_out_history
    }

    companion object {
        fun newInstance(): ReceiptInOutHistoryFragment {
            return ReceiptInOutHistoryFragment()
        }
    }
}