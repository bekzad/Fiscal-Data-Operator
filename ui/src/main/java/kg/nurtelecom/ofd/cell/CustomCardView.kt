package kg.nurtelecom.ofd.cell

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.google.android.material.card.MaterialCardView
import kg.nurtelecom.ui.R
import kg.nurtelecom.ui.databinding.ViewCardBinding
import java.math.BigDecimal
import java.math.RoundingMode

class CustomCardView(context: Context, attrs: AttributeSet? = null) :
    MaterialCardView(context, attrs) {

    private val binding = ViewCardBinding
        .inflate(LayoutInflater.from(context), this, true)

    fun setCardTitle(@StringRes title: Int) {
        binding.cardTitle.setText(title)
    }

    fun setCardContent(value: BigDecimal) {
        binding.cardContent.text = value.setScale(2, RoundingMode.CEILING).toString()

    }

    fun setContentColor(@ColorRes color: Int) {
        binding.cardContent.setTextColor(resources.getColor(color))
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
