package kg.nurtelecom.sell.ui.fragment.other_operations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.SideMenuOtherOperationsBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.fragment.other_operations.prepayment.RefundPrepaymentFragment

class OtherOperationsFragment : CoreFragment<SideMenuOtherOperationsBinding>() {

    override val vm: SellMainViewModel by activityViewModels()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): SideMenuOtherOperationsBinding {
        return SideMenuOtherOperationsBinding.inflate(layoutInflater)
    }

    override fun setupToolbar(): Int  = R.string.history_title

    override fun setupViews() {
        setHasOptionsMenu(true)
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        vb.btnMenuItemRefundPrepayment.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()?.replace(R.id.sell_container, RefundPrepaymentFragment.newInstance())
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    companion object {
        fun newInstance(): OtherOperationsFragment {
            return OtherOperationsFragment()
        }
    }
}