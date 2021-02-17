package kg.nurtelecom.sell.ui.fragment.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.data.z_report.ReportDetailed
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.BottomSheetFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.fragment.other_operations.OtherOperationsFragment
import kg.nurtelecom.sell.ui.fragment.report.ZReportFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetFragment : BottomSheetDialogFragment() {

    private val vm: SellMainViewModel by viewModel()
    private val vb: BottomSheetFragmentBinding by lazy { BottomSheetFragmentBinding.inflate(layoutInflater) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showsDialog = true
        vb.tvMenuItemClose.setOnClickListener {
            parentActivity.replaceFragment<ZReportFragment>(R.id.sell_container)
            dismiss()
        }
        vb.btnMenuItemCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return vb.root
    }

    companion object {
        fun newInstance(supportFragmentManager: FragmentManager) {
            return BottomSheetFragment().show(supportFragmentManager, "BottomSheetFragment")
        }
    }
}