package kg.nurtelecom.ofd.ui.edittext

import android.content.Context

import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.ui.databinding.InputTextFieldBinding

class InputTextField (context: Context, attr: AttributeSet) : ConstraintLayout(context, attr) {

    private val binding by lazy {
        InputTextFieldBinding.inflate(
            LayoutInflater.from(context), this, true
        )
    }

    init {

        setPasswordEditText(true)

    }

    fun setInputType(type: Int) {
        binding.etInputText.inputType = type
    }

    fun setHint(text: String) {
        binding.etInputText.hint = text
    }

    fun setPasswordEditText(value: Boolean) {
        binding.textInputLayout.isPasswordVisibilityToggleEnabled = value

    }

    fun setOnTextChanged(listener: TextWatcher) {
        binding.etInputText.addTextChangedListener(listener)
    }

}
