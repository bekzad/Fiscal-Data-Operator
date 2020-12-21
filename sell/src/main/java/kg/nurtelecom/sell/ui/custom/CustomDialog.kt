package kg.nurtelecom.sell.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.DialogViewBinding


class CustomDialog(context: Context, attributeSet: AttributeSet? = null) : ConstraintLayout(context, attributeSet) {

    private val binding = DialogViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setDialogTitle(@StringRes text: Int = R.string.fiscal_regime) {
        binding.regimeTv.setText(text)
    }

    fun setDialogContent(@StringRes text: Int = R.string.fiscal_regime_content) {
        binding.regimeMessage.setText(text)
    }

    fun setCardVisibility(visibility: Boolean) {
        when (visibility) {
            true -> binding.root.visibility = View.VISIBLE
            else -> binding.root.visibility = View.GONE
        }
    }

}