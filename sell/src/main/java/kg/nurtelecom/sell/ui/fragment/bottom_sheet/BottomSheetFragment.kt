package kg.nurtelecom.sell.ui.fragment.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.nurtelecom.sell.databinding.BottomSheetFragmentBinding

class BottomSheetFragment : BottomSheetDialogFragment(){

    private var bottomSheetFragmentBinding: BottomSheetFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bottomSheetFragmentBinding = BottomSheetFragmentBinding.inflate(inflater, container, false)
        return bottomSheetFragmentBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = BottomSheetFragmentBinding.bind(view)
        bottomSheetFragmentBinding = binding

        bottomSheetFragmentBinding!!.sellMainMenuItemClose.setOnClickListener {
            // here place for replacing fragment
        }
        bottomSheetFragmentBinding!!.sellMainMenuItemCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        bottomSheetFragmentBinding = null
        super.onDestroyView()
    }
}