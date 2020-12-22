package kg.nurtelecom.ofd.edittext

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import kg.nurtelecom.ui.databinding.EditTextViewBinding
import java.math.BigDecimal

class CustomCardEditText(context: Context, attributeSet: AttributeSet? = null) :
    ConstraintLayout(context, attributeSet) {

    private val binding = EditTextViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setTitle(@StringRes text: Int) {
        binding.priceTv.setText(text)
    }

    fun setTitleColor(@ColorInt color: Int) {
        binding.priceTv.setTextColor(color)
    }

    fun setContentColor(@ColorInt color: Int) {
        binding.valueEt.setTextColor(color)
    }

    fun fetchTextState(action: (text: CharSequence?) -> Unit) {
        binding.valueEt.doOnTextChanged { text, _, _, _ ->
            action(text)
        }
    }

    fun fetchInputData(): Double? {
        return binding.valueEt.text.toString().toDoubleOrNull()
    }
}
