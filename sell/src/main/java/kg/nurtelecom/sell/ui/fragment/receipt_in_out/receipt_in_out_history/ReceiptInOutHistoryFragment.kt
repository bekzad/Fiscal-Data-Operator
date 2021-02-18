package kg.nurtelecom.sell.ui.fragment.receipt_in_out.receipt_in_out_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutResult
import kg.nurtelecom.ofd.item_decoration.RoundDecor
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.core.ItemClickListener
import kg.nurtelecom.sell.databinding.FragmentReceiptInOutHistoryBinding
import kg.nurtelecom.sell.ui.fragment.adapter.ReceiptInOutAdapter
import kg.nurtelecom.sell.ui.fragment.receipt_in_out.ReceiptInOutDetailFragment

class ReceiptInOutHistoryFragment : CoreFragment<FragmentReceiptInOutHistoryBinding, ReceiptInOutHistoryVM>(
    ReceiptInOutHistoryVM::class), ItemClickListener {

    private val historyListAdapter = ReceiptInOutAdapter(this)

    override fun setupViews() {
        super.setupViews()
        vm.receiptInOutFetched.value = null
        setupRV()
    }

    override fun onResume() {
        super.onResume()
        vm.fetchReceiptInOutHistoryList()
    }

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentReceiptInOutHistoryBinding {
        return FragmentReceiptInOutHistoryBinding.inflate(layoutInflater)
    }

    override fun setupToolbar(): Int {
        return R.string.receipt_in_out_history
    }

    private fun setupRV() {
        vb.rvHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(RoundDecor())
            adapter = historyListAdapter
        }
    }

    override fun <T> onItemClick(value: T, isChecked: Boolean) {
        vm.fetchReceiptInOutById(value as Long)
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        vm.receiptInOutHistoryList.observe(this, {
            historyListAdapter.updateDataSource(it)
        })

        vm.receiptInOutFetched.observe(viewLifecycleOwner, {
            when (it) {
                is ReceiptInOutResult -> {
                    parentActivity.replaceFragment<ReceiptInOutDetailFragment>(R.id.sell_container, args = { bundleOf(Pair("receipt", it)) } )
                }
            }
        })
    }

    companion object {
        fun newInstance(): ReceiptInOutHistoryFragment {
            return ReceiptInOutHistoryFragment()
        }
    }
}