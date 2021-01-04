package kg.nurtelecom.ofd.sell

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import kg.nurtelecom.ui.databinding.InfoCardCellViewBinding
import java.math.BigDecimal

class InfoCardCellView(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {

    private val binding = InfoCardCellViewBinding
        .inflate(LayoutInflater.from(context), this, true)

    fun setTitle(@StringRes title: Int) {
        binding.cardTitle.setText(title)
    }

    fun setContent(value: BigDecimal) {
        binding.cardContent.setText(value.toString())
    }

    fun setTextColor(@ColorRes colorId: Int) {
        binding.cardTitle.setTextColor(ContextCompat.getColor(context, colorId))
        binding.cardContent.setTextColor(ContextCompat.getColor(context, colorId))
    }

    fun addNextIcon() {
        binding.cardNextIcon.visibility = VISIBLE
    }

    fun setBackground(@ColorRes colorId: Int) {
        binding.container.setBackgroundColor(ContextCompat.getColor(context, colorId))
    }

    fun isEditable(editable: Boolean) {
        binding.cardContent.isEnabled = editable
    }

    fun fetchTextState(action: (text: CharSequence?) -> Unit) {
        binding.cardContent.doOnTextChanged { text, _, _, _ ->
            action(text)
        }
    }

    fun fetchInputData(): BigDecimal? {
        return binding.cardContent.text.toString().toBigDecimalOrNull()
    }
}