package kg.nurtelecom.sell.ui.fragment.receipt_in_out

import android.view.LayoutInflater
import android.view.ViewGroup
import kg.nurtelecom.core.fragment.SimpleFragment
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.FragmentReceiptInOutDetailBinding

class ReceiptInOutDetailFragment : CoreFragment<FragmentReceiptInOutDetailBinding, ReceiptInOutVM>(ReceiptInOutVM::class) {

    override fun setupViews() {
        super.setupViews()
        vb.tvReceiptDetail.text = vm.selectedReceiptInOut.toString()

        vm.selectedReceiptInOut = null
        vm.event.postValue(null)
    }

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentReceiptInOutDetailBinding {
        return FragmentReceiptInOutDetailBinding.inflate(layoutInflater)
    }

    override fun setupToolbar(): Int {
        return R.string.print_check
    }

    companion object {
        fun newInstance(): ReceiptInOutDetailFragment {
            return ReceiptInOutDetailFragment()
        }
    }

}