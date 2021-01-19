package kg.nurtelecom.sell.ui.fragment.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.nurtelecom.sell.databinding.BottomSheetFragmentBinding

class BottomSheetFragment : BottomSheetDialogFragment(){

    private var _bottomSheetFragmentBinding: BottomSheetFragmentBinding? = null
    private val bottomSheetFragmentBinding get() = _bottomSheetFragmentBinding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bottomSheetFragmentBinding = BottomSheetFragmentBinding.inflate(inflater, container, false)
        return bottomSheetFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomSheetFragmentBinding.tvMenuItemClose.setOnClickListener {
            // here place for replacing fragment
        }
        bottomSheetFragmentBinding.btnMenuItemCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        _bottomSheetFragmentBinding = null
        super.onDestroyView()
    }
}