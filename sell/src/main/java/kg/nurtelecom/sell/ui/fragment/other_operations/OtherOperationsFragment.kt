package kg.nurtelecom.sell.ui.fragment.other_operations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.OtherOperationsFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.fragment.other_operations.prepayment.RefundPrepaymentFragment

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
            // here place for replacing fragment
        }
        vb.btnMenuItemCloseCredit.setOnClickListener{
            // here place for replacing fragment
        }
        vb.btnMenuItemRepaymentCredit.setOnClickListener{
            // here place for replacing fragment
        }
        vb.btnMenuItemDepositAndPayment.setOnClickListener{
            // here place for replacing fragment
        }
        vb.btnMenuItemHistoryDepositsPayments.setOnClickListener{
            // here place for replacing fragment
        }
        vb.btnMenuItemFiscalReport.setOnClickListener{
            // here place for replacing fragment
        }
    }

    companion object {
        fun newInstance(): OtherOperationsFragment {
            return OtherOperationsFragment()
        }
    }
}