package kg.nurtelecom.sell.ui.fragment.bottom_sheet

import android.util.Log
import kg.nurtelecom.core.fragment.CoreBottomSheetFragment
import kg.nurtelecom.data.z_report.ReportDetailed
import kg.nurtelecom.sell.databinding.BottomSheetFragmentBinding
import kg.nurtelecom.sell.ui.fragment.report.SessionViewModel


class BottomSheetFragment : CoreBottomSheetFragment<BottomSheetFragmentBinding, SessionViewModel>(SessionViewModel::class) {

    override fun setupViews() {
        vb.tvMenuItemClose.setOnClickListener {
            vm.closeSession()
        }
        vb.btnMenuItemCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        vm.sessionReportData.observe(this, {
            when(it) {
                is ReportDetailed -> {
                    dismiss()
                    activity?.finish()
                }
            }
        })
    }

    override fun getBinding() = BottomSheetFragmentBinding.inflate(layoutInflater)
}