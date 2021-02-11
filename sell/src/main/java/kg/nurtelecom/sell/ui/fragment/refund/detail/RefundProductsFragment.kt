package kg.nurtelecom.sell.ui.fragment.refund.detail

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.data.history_by_id.ReceiptItems
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.ItemClickListener
import kg.nurtelecom.sell.databinding.FragmentRefundProductsBinding
import kg.nurtelecom.sell.ui.fragment.history.HistoryViewModel
import kg.nurtelecom.sell.ui.fragment.refund.RefundFragment.Companion.CHECK_ID
import java.math.BigDecimal

class RefundProductsFragment : CoreFragment<FragmentRefundProductsBinding, HistoryViewModel>(HistoryViewModel::class), ItemClickListener {

    private var refundAdapter: RefundAdapter = RefundAdapter(this)

    override fun subscribeToLiveData() {
        vm.detailCheckHistory.observe(viewLifecycleOwner) {
            parentActivity.setToolbarTitle(getString(R.string.cash_check_number, it.receipt.indexNum))

            refundAdapter.setListData(it.receipt.receiptItems as ArrayList<ReceiptItems>)
            vb.rvRefund.adapter = refundAdapter
        }
        vm.totalSum.observe(viewLifecycleOwner) { taxSum ->
            vb.icSumPay.setContent(taxSum)
        }
    }

    override fun setupViews() {
        val id = requireArguments().getInt(CHECK_ID)
        initRecyclerView()
        vm.fetchDetailCheckHistory(id)
    }

    override fun <T> onItemClick(value: T) {
        vm.calculateTotalSum(value as BigDecimal)
    }

    private fun initRecyclerView() {
        vb.rvRefund.adapter = refundAdapter
        vb.rvRefund.layoutManager = LinearLayoutManager(activity)
        vb.rvRefund.addItemDecoration(DefaultItemDecorator(18, 8))
    }

    override fun getBinding(): FragmentRefundProductsBinding {
        return FragmentRefundProductsBinding.inflate(layoutInflater)
    }

    companion object {
        fun newInstance(): RefundProductsFragment {
            return RefundProductsFragment()
        }
    }

    class DefaultItemDecorator(private val horizontalSpacing: Int, private val verticalSpacing: Int) :
            RecyclerView.ItemDecoration(){

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent,
                    state)
            outRect.right = horizontalSpacing
            outRect.left = horizontalSpacing
            if (parent.getChildLayoutPosition(view) ==
                    0) {
                outRect.top = verticalSpacing
            }
            outRect.bottom = verticalSpacing
        }
    }
}