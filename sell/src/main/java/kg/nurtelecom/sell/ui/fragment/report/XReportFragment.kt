package kg.nurtelecom.sell.ui.fragment.report

import android.view.LayoutInflater
import android.view.ViewGroup
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.XReportFragmentBinding

class XReportFragment: CoreFragment<XReportFragmentBinding, XReportViewModel>(XReportViewModel::class) {

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): XReportFragmentBinding {
        return XReportFragmentBinding.inflate(layoutInflater)
    }

    override fun setupToolbar(): Int  = R.string.text_x_report

    override fun setupViews() {
        vm.fetchReportSession()
    }

    override fun subscribeToLiveData() {
        vm.sessionReportData.observe(viewLifecycleOwner, {})
    }

    companion object {
        fun newInstance(): XReportFragment {
            return XReportFragment()
        }
    }
}