package kg.nurtelecom.sell.ui.fragment.refund.detail

import androidx.recyclerview.widget.LinearLayoutManager
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.data.history_by_id.ReceiptItems
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.FragmentRefundProductsBinding
import kg.nurtelecom.sell.ui.fragment.history.HistoryViewModel

class RefundProductsFragment : CoreFragment<FragmentRefundProductsBinding, HistoryViewModel>(HistoryViewModel::class) {

    private var refundAdapter: RefundAdapter = RefundAdapter()

    override fun subscribeToLiveData() {
        vm.detailCheckHistory.observe(viewLifecycleOwner) {
            parentActivity.setToolbarTitle(getString(R.string.cash_check_number, it.receipt.indexNum))

            refundAdapter.setListData(it.receipt.receiptItems as ArrayList<ReceiptItems>)
            vb.rvRefund.adapter = refundAdapter
        }
    }

    override fun setupViews() {
//        val id = requireArguments().getInt(CHECK_ID)
        initRecyclerView()
//        vm.fetchDetailCheckHistory(id)
    }

    private fun initRecyclerView() {
        vb.rvRefund.adapter = refundAdapter
        vb.rvRefund.layoutManager = LinearLayoutManager(activity)
    }

    override fun getBinding(): FragmentRefundProductsBinding {
        return FragmentRefundProductsBinding.inflate(layoutInflater)
    }

    companion object {
        fun newInstance(): RefundProductsFragment {
            return RefundProductsFragment()
        }
    }
}