package kg.nurtelecom.sell.ui.fragment.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.nurtelecom.data.z_report.ReportDetailed
import kg.nurtelecom.sell.databinding.BottomSheetFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetFragment : BottomSheetDialogFragment() {

    private val vm: SellMainViewModel by viewModel()
    private val vb: BottomSheetFragmentBinding by lazy { BottomSheetFragmentBinding.inflate(layoutInflater) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeToLiveData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showsDialog = true
        vb.tvMenuItemClose.setOnClickListener {
            vm.closeSession()
        }
        vb.btnMenuItemCancel.setOnClickListener {
            dismiss()
        }
        return vb.root
    }

    fun subscribeToLiveData() {
        vm.sessionReportData.observe(this, {
            when(it) {
                is ReportDetailed -> {
                    dismiss()
                    activity?.finish()
                }
            }
        })
    }
}