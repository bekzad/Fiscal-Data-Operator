package kg.nurtelecom.sell.ui.fragment.print_receipt

import android.view.LayoutInflater
import android.view.ViewGroup
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.FragmentSaveReceiptBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel

class SaveReceiptFragment : CoreFragment<FragmentSaveReceiptBinding, SellMainViewModel>(SellMainViewModel::class) {

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSaveReceiptBinding = FragmentSaveReceiptBinding.inflate(inflater, container, false)

    override fun setupToolbar(): Int = R.string.print_check

    override fun setupViews() {
        super.setupViews()

        vb.btnPrintCheck.setOnClickListener {
            navigateToPrintReceipt()
        }
    }

    override fun subscribeToLiveData() {
        vm.fetchReceiptResultString.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                vb.tvHeaders.text = response.body()!!
                vb.tvCode.text = response.code().toString()
            } else {
                vb.tvCode.text = response.code().toString()
                vb.tvCashierCheck.text = response.errorBody()!!.string()
            }
        })
    }

    private fun navigateToPrintReceipt() {
        parentActivity.replaceFragment<PrintReceiptFragment>(R.id.sell_container, true)
    }

    companion object {
        fun newInstance() = SaveReceiptFragment()
    }
}