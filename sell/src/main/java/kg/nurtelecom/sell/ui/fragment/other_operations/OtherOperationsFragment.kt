package kg.nurtelecom.sell.ui.fragment.other_operations

import android.view.LayoutInflater
import android.view.ViewGroup
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.data.enums.OperationType
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.OtherOperationsFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.fragment.credit.CreditListFragment
import kg.nurtelecom.sell.ui.fragment.other_operations.prepayment.RefundPrepaymentFragment
import kg.nurtelecom.sell.ui.fragment.payment_method.PaymentMethodFragment
import kg.nurtelecom.sell.ui.fragment.receipt_in_out.receipt_in_out.ReceiptInOutFragment
import kg.nurtelecom.sell.ui.fragment.receipt_in_out.receipt_in_out_history.ReceiptInOutHistoryFragment
import kg.nurtelecom.sell.ui.fragment.sell.SellFragment

class OtherOperationsFragment : CoreFragment<OtherOperationsFragmentBinding, SellMainViewModel>(SellMainViewModel::class) {

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): OtherOperationsFragmentBinding {
        return OtherOperationsFragmentBinding.inflate(layoutInflater)
    }

    override fun setupToolbar(): Int  = R.string.history_title

    override fun setupViews() {
        setHasOptionsMenu(true)
        vb.btnMenuItemPurchase.setOnClickListener{
            // here place for replacing fragment
        }
        vb.btnMenuItemReturnPurchase.setOnClickListener{
            // here place for replacing fragment
        }
        vb.btnMenuItemRefundPrepayment.setOnClickListener {
            parentActivity.replaceFragment<RefundPrepaymentFragment>(R.id.sell_container)
        }
        vb.btnMenuItemCredit.setOnClickListener{
            vm.operationType = OperationType.POSTPAY
            parentActivity.replaceFragment<SellFragment>(R.id.sell_container)
        }
        vb.btnMenuItemCloseCredit.setOnClickListener{
            parentActivity.replaceFragment<CreditListFragment>(R.id.sell_container)
        }
        vb.btnMenuItemRepaymentCredit.setOnClickListener{
            // here place for replacing fragment
        }
        vb.btnMenuItemDepositAndPayment.setOnClickListener{
            parentActivity.replaceFragment<ReceiptInOutFragment>(R.id.sell_container)
        }
        vb.btnMenuItemHistoryDepositsPayments.setOnClickListener{
            parentActivity.replaceFragment<ReceiptInOutHistoryFragment>(R.id.sell_container)
        }
        vb.btnMenuItemFiscalReport.setOnClickListener{
            // here place for replacing fragment
        }
        vb.btnMenuItemPrepayment.setOnClickListener {
            vm.operationType = OperationType.PREPAY
            parentActivity.replaceFragment<PaymentMethodFragment>(R.id.sell_container)
        }
    }

    companion object {
        fun newInstance(): OtherOperationsFragment {
            return OtherOperationsFragment()
        }
    }
}