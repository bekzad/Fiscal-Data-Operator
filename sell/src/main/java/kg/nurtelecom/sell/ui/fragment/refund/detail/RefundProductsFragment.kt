package kg.nurtelecom.sell.ui.fragment.refund.detail

import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import kg.nurtelecom.core.CoreEvent
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.data.history_by_id.ReceiptItems
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.ItemClickListener
import kg.nurtelecom.sell.databinding.FragmentRefundProductsBinding
import kg.nurtelecom.sell.ui.activity.SellMainActivity
import kg.nurtelecom.sell.ui.fragment.history.HistoryViewModel
import kg.nurtelecom.sell.ui.fragment.payment_method.PaymentMethodFragment
import kg.nurtelecom.sell.ui.fragment.refund.RefundFragment.Companion.CHECK_ID
import java.math.BigDecimal

class RefundProductsFragment : CoreFragment<FragmentRefundProductsBinding, HistoryViewModel>(HistoryViewModel::class), ItemClickListener {

    private var refundAdapter: RefundAdapter = RefundAdapter(this)

    override fun subscribeToLiveData() {
        vm.event.observe(this, { it ->
            when (it) {
                is CoreEvent.Loading -> {
                    (activity as SellMainActivity?)?.isProgressBarVisible(true)
                    (activity as SellMainActivity?)?.setProgressBarColor(R.color.green)
                }
                is CoreEvent.Success -> {
                    vm.detailCheckHistory.observe(viewLifecycleOwner) {
                        parentActivity.setToolbarTitle(getString(R.string.cash_check_number, it.receipt.indexNum))
                        refundAdapter.setListData(it.receipt.receiptItems as ArrayList<ReceiptItems>)
                        vb.rvRefund.adapter = refundAdapter
                    }
                    (activity as SellMainActivity?)?.isProgressBarVisible(false)
                }
                is CoreEvent.Error -> {
                    (activity as SellMainActivity?)?.setProgressBarColor(R.color.red)
                    Handler().postDelayed({
                        (activity as SellMainActivity?)?.isProgressBarVisible(false)
                    }, 1000)
                }
            }
        })
        vm.totalSum.observe(viewLifecycleOwner) { sum ->
            if (sum.compareTo(BigDecimal.ZERO) == 0) {
                vb.icSumPay.setContent(BigDecimal.ZERO)
                vb.icSumPay.changeEditText(true)
            } else {
                vb.icSumPay.setContent(sum)
                vb.icSumPay.changeEditText(false, resources.getColor(R.color.orange))
            }
        }
    }

    override fun setupViews() {
        val id = requireArguments().getInt(CHECK_ID)
        initRecyclerView()
        vm.fetchDetailCheckHistory(id)
        vb.icSumPay.setOnClickListener {
            parentActivity.replaceFragment<PaymentMethodFragment>(R.id.sell_container)
        }
    }

    override fun <T> onItemClick(value: T, isChecked: Boolean) {
        vm.calculateTotalSum(value as ReceiptItems, isChecked)
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

    override fun onResume() {
        super.onResume()
        vm.resetTotalSum()
        vb.icSumPay.changeEditText(true)
    }
}