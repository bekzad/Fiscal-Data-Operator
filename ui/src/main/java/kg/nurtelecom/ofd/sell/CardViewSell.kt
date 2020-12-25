package kg.nurtelecom.ofd.sell

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import kg.nurtelecom.ui.databinding.CardViewSellBinding

class CardViewSell(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {

    private val binding = CardViewSellBinding
        .inflate(LayoutInflater.from(context), this, true)

    fun setTitle(@StringRes title: Int) {
        binding.cardTitle.setText(title)
    }

    fun setContent(value: Double) {
        binding.cardContent.setText(value.toString())
    }

    fun setTextColor(@ColorInt color: Int) {
        binding.cardTitle.setTextColor(color)
        binding.cardContent.setTextColor(color)
    }

    fun addNextIcon() {
        binding.cardNextIcon.visibility = VISIBLE
    }

    fun setBackground(@DrawableRes resourceId: Int) {
        binding.cardView.setBackgroundResource(resourceId)
    }

    fun isEditable(editable: Boolean) {
        binding.cardContent.isEnabled = editable
    }

    fun fetchTextState(action: (text: CharSequence?) -> Unit) {
        binding.cardContent.doOnTextChanged { text, _, _, _ ->
            action(text)
        }
    }

    fun fetchInputData(): Double? {
        return binding.cardContent.text.toString().toDoubleOrNull()
    }
}