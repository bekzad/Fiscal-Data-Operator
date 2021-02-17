package kg.nurtelecom.sell.ui.fragment.receipt_in_out.receipt_in_out_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutHistoryModel
import kg.nurtelecom.ofd.item_decoration.RoundDecor
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.FragmentReceiptInOutHistoryBinding
import kg.nurtelecom.sell.ui.fragment.adapter.ReceiptInOutAdapter

class ReceiptInOutHistoryFragment : CoreFragment<FragmentReceiptInOutHistoryBinding, ReceiptInOutHistoryVM>(
    ReceiptInOutHistoryVM::class) {

    private val historyListAdapter = ReceiptInOutAdapter(listOf())

    override fun setupViews() {
        super.setupViews()
        setupRV()
    }

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentReceiptInOutHistoryBinding {
        return FragmentReceiptInOutHistoryBinding.inflate(layoutInflater)
    }

    override fun setupToolbar(): Int {
        return R.string.receipt_in_out_history
    }

    private fun setupRV() {
        vm.fetchReceiptInOutHistoryList()
        vb.rvHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(RoundDecor())
            adapter = historyListAdapter
        }
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        vm.receiptInOutHistoryList.observe(this, {
            historyListAdapter.updateDataSource(it)
        })
    }

    companion object {
        fun newInstance(): ReceiptInOutHistoryFragment {
            return ReceiptInOutHistoryFragment()
        }
    }
}