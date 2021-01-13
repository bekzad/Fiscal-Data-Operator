package kg.nurtelecom.ofd.edittext

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isEmpty
import com.google.android.material.textfield.TextInputLayout
import kg.nurtelecom.ui.databinding.InputTextFieldBinding

class InputTextField (context: Context, attr: AttributeSet) : ConstraintLayout(context, attr) {

    var binding: InputTextFieldBinding =
        InputTextFieldBinding.inflate(LayoutInflater.from(context), this, true)

    init {

        setPasswordEditText(TextInputLayout.END_ICON_NONE)
    }

    fun setInputType(type: Int) {
        binding.etInputText.inputType = type
    }

    fun setHint(text: String) {
        binding.etInputText.hint = text
    }

    fun setPasswordEditText(endIconMode: Int) {
        binding.textInputLayout.endIconMode = endIconMode
    }

    fun setOnTextChanged(listener: TextWatcher) {
        binding.etInputText.addTextChangedListener(listener)
    }

    fun checkingTheField() {
        if (binding.etInputText.text.toString().isEmpty()) {
            error("empty field")
        }
    }
}
