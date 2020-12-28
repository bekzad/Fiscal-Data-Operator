package kg.nurtelecom.ofd.sell

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.google.android.material.card.MaterialCardView
import kg.nurtelecom.ui.databinding.ViewCardBinding

class CustomCardView(context: Context, attrs: AttributeSet? = null) :
    MaterialCardView(context, attrs) {

    private val binding = ViewCardBinding
        .inflate(LayoutInflater.from(context), this, true)

    fun setCardTitle(@StringRes title: Int) {
        binding.cardTitle.setText(title)
    }

    fun setCardContent(value: Double) {
        binding.cardContent.text = value.toString()
    }

    fun setContentTextSize(@DimenRes size: Int) {
        binding.cardContent.textSize = size.toFloat()
    }

    fun addNextIcon() {
        binding.cardNextIcon.visibility = VISIBLE
    }

    fun setDeleteIcon() {
        binding.deleteIcon.visibility = VISIBLE
    }

    fun setBackground(@DrawableRes resourceId: Int) {
        binding.cardView.setBackgroundResource(resourceId)
    }
}