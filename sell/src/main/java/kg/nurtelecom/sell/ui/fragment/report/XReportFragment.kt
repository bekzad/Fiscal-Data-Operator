package kg.nurtelecom.sell.ui.fragment.report

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.XReportFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel

class XReportFragment: CoreFragment<XReportFragmentBinding>() {

    override val vm: SellMainViewModel by activityViewModels()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): XReportFragmentBinding {
        return XReportFragmentBinding.inflate(layoutInflater)
    }

    override fun setupToolbar(): Int  = R.string.text_x_report

    override fun setupViews() {
        super.setupViews()
        vm.fetchReportSession()
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        vm.sessionReportData.observe(this, {})
    }

    companion object {
        fun newInstance(): XReportFragment {
            return XReportFragment()
        }
    }
}