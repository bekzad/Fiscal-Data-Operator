package kg.nurtelecom.ofd.ui.edittext

import android.content.Context
import android.text.TextWatcher
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

    fun setHint(text: String) {
        binding.etInputText.hint = text
    }

    fun setEyesIconIsVisible (value: Boolean){
        if (value) {
            binding.inputFieldIcon.visibility = VISIBLE
        }else{
            binding.inputFieldIcon.visibility = GONE
        }
    }

    fun setOnTextChanged (listener: TextWatcher){
        binding.etInputText.addTextChangedListener(listener)
    }

    fun setIcon (resId: Int){
        binding.inputFieldIcon.setImageResource(resId)
    }
}