package kg.nurtelecom.sell.ui.fragment.receipt_in_out


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutType
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.FragmentReceiptInOutBinding
import java.math.BigDecimal

class ReceiptInOutFragment : CoreFragment<FragmentReceiptInOutBinding, ReceiptInOutVM>(ReceiptInOutVM::class) {


    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentReceiptInOutBinding {
        return FragmentReceiptInOutBinding.inflate(layoutInflater)
    }

    override fun setupToolbar(): Int {
        return R.string.receipt_in_out
    }

    override fun setupViews() {
        super.setupViews()
        vb.btnConfirm.setOnClickListener {
            vm.generateReceiptInOut()
        }

        vb.btnReceiptIn.setOnClickListener {
            setupIncome()
        }

        vb.btnReceiptOut.setOnClickListener {
            setupOutcome()
        }

        vb.icInputSum.fetchTextState {
            if (!it.isNullOrEmpty()) {
                vm.sum = BigDecimal(it.toString())
            }
        }
    }

    private fun setupIncome() {
        vb.btnReceiptIn.apply {
            setTextColor(resources.getColor(R.color.white))
            backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.green)
        }
        vb.btnReceiptOut.apply {
            setTextColor(resources.getColor(R.color.black))
            backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.white)
        }
        vm.receiptInOutType = ReceiptInOutType.INCOME
    }

    private fun setupOutcome() {
        vb.btnReceiptOut.apply {
            setTextColor(resources.getColor(R.color.white))
            backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.green)
        }
        vb.btnReceiptIn.apply {
            setTextColor(resources.getColor(R.color.black))
            backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.white)
        }
        vm.receiptInOutType = ReceiptInOutType.OUTCOME
    }

    companion object {
        fun newInstance(): ReceiptInOutFragment {
            return ReceiptInOutFragment()
        }
    }

}