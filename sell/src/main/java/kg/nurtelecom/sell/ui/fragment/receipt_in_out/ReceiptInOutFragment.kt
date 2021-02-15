package kg.nurtelecom.sell.ui.fragment.receipt_in_out


import android.view.LayoutInflater
import android.view.ViewGroup
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.FragmentReceiptInOutBinding

class ReceiptInOutFragment : CoreFragment<FragmentReceiptInOutBinding, ReceiptInOutVM>(ReceiptInOutVM::class) {


    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentReceiptInOutBinding {
        return FragmentReceiptInOutBinding.inflate(layoutInflater)
    }

    override fun setupToolbar(): Int {
        return R.string.receipt_in_out
    }

}