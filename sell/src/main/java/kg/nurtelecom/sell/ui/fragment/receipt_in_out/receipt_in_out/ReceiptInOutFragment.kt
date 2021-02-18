package kg.nurtelecom.sell.ui.fragment.receipt_in_out.receipt_in_out


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import kg.nurtelecom.core.extension.enable
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutResult
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutType
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.FragmentReceiptInOutBinding
import kg.nurtelecom.sell.ui.fragment.receipt_in_out.ReceiptInOutDetailFragment
import java.math.BigDecimal

class ReceiptInOutFragment : CoreFragment<FragmentReceiptInOutBinding, ReceiptInOutVM>(
        ReceiptInOutVM::class) {

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentReceiptInOutBinding {
        return FragmentReceiptInOutBinding.inflate(layoutInflater)
    }

    override fun setupToolbar(): Int {
        return R.string.receipt_in_out
    }

    override fun setupViews() {
        super.setupViews()
        vm.receiptInOutCreated.value = null
        vb.btnConfirm.enable(false)

        vb.btnConfirm.setOnClickListener {
            vm.generateReceiptInOut(BigDecimal(vb.icInputSum.getContent()))
        }

        vb.btnReceiptIn.setOnClickListener {
            setupIncome()
        }

        vb.btnReceiptOut.setOnClickListener {
            setupOutcome()
        }

        vb.icInputSum.fetchTextState {
            if (!it.isNullOrEmpty()) {
                vb.btnConfirm.enable(true)
            } else {
                vb.btnConfirm.enable(false)
            }
        }
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        vm.receiptInOutCreated.observe(viewLifecycleOwner, {
            when (it) {
                is ReceiptInOutResult -> {
                    vb.icInputSum.eraseContent()
                    parentActivity.replaceFragment<ReceiptInOutDetailFragment>(R.id.sell_container, args = {bundleOf(Pair("receipt", it))} )
                }
            }
        })
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