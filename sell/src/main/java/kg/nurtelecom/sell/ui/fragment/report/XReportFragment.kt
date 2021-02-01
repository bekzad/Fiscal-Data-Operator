package kg.nurtelecom.sell.ui.fragment.report

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.XReportFragmentBinding

class XReportFragment: CoreFragment<XReportFragmentBinding, SessionViewModel>(SessionViewModel::class) {

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).setToolbarTitle(R.string.text_x_report)
    }

    override fun getBinding(): XReportFragmentBinding {
        return XReportFragmentBinding.inflate(layoutInflater)
    }

    override fun setupViews() {
        super.setupViews()
        vm.fetchReportSession()
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        vm.sessionReportData.observe(this, {
            Log.d("Session Report", it.toString())
        })
    }

    companion object {
        fun newInstance(): XReportFragment {
            return XReportFragment()
        }
    }
}