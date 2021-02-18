package kg.nurtelecom.sell.ui.fragment.report

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import kg.nurtelecom.core.CoreEvent
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.ReportFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainActivity
import kg.nurtelecom.sell.ui.activity.SellMainViewModel

class XReportFragment: CoreFragment<ReportFragmentBinding, SellMainViewModel>(SellMainViewModel::class) {

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ReportFragmentBinding {
        return ReportFragmentBinding.inflate(layoutInflater)
    }

    override fun setupToolbar(): Int  = R.string.text_x_report

    override fun setupViews() {
        vm.fetchReportSession()
    }

    override fun subscribeToLiveData() {
        vm.event.observe(this, {
            when (it) {
                is CoreEvent.Loading -> {
                    (activity as SellMainActivity?)?.isProgressBarVisible(true)
                    (activity as SellMainActivity?)?.setProgressBarColor(R.color.green)
                }
                is CoreEvent.Success -> {
                    vm.sessionReportData.observe(viewLifecycleOwner, {})
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
    }

    companion object {
        fun newInstance(): XReportFragment {
            return XReportFragment()
        }
    }
}