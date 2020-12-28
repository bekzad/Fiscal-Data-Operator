package kg.nurtelecom.ofd.ui.edittext

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.ui.R
import kg.nurtelecom.ui.databinding.InputTextFieldBinding

class InputTextField (context: Context, attr: AttributeSet) : ConstraintLayout(context, attr) {

    private val binding by lazy {
        InputTextFieldBinding.inflate(
            LayoutInflater.from(context), this, true
        )
    }
    init {
        binding.inputFieldIcon.setOnClickListener {
            setIcon(R.drawable.ic_baseline_visibility_off)
        }
    }
    fun setIcon (resId: Int){
        binding.inputFieldIcon.setImageResource(resId)
    }
}