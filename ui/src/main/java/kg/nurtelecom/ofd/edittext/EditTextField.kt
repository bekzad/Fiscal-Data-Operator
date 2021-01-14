package kg.nurtelecom.ofd.edittext

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout
import kg.nurtelecom.ui.R
import kg.nurtelecom.ui.databinding.EditTextFieldBinding

class EditTextField(context: Context, attr: AttributeSet) : TextInputLayout(context, attr) {
    private val vb = EditTextFieldBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.theme.obtainStyledAttributes(
            attr,
            R.styleable.EditTextField,
            0, 0
        ).apply {
            try {
                setInputType(getInt(R.styleable.EditTextField_android_inputType, InputType.TYPE_CLASS_TEXT))
                setHint(getString(R.styleable.EditTextField_hint_text))
                setText(getString(R.styleable.EditTextField_text))
                setErrorMessage(getString(R.styleable.EditTextField_error_text))
                setHelperMessage(getString(R.styleable.EditTextField_message_text))
            } finally {
                recycle()
            }
        }
    }

    private fun setInputType(type: Int) {
        vb.textField.inputType = type
        if (type == (InputType.TYPE_TEXT_VARIATION_PASSWORD + 1)) {
            vb.textFieldLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
        } else {
            vb.textFieldLayout.endIconMode = TextInputLayout.END_ICON_NONE
        }
    }

    fun setHint(hint: String?) {
        vb.textField.hint = hint
    }

    fun setText(text: String?) {
        vb.textField.setText(text)
    }

    fun setErrorMessage(message: String?) {
        vb.textFieldLayout.error = message
    }

    fun setHelperMessage(message: String?) {
        vb.textFieldLayout.helperText = message
    }

    fun getText(): String {
        return vb.textField.text.toString()
    }

    fun setTextChangedListener(listener: () -> Unit) {
        vb.textField.addTextChangedListener { listener() }
    }

    fun isFieldEmpty(): Boolean {
        return this.getText().isEmpty()
    }
}