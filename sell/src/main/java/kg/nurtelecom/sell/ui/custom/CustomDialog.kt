package kg.nurtelecom.sell.ui.custom

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.DialogViewBinding

// TODO: must be change
class CustomDialog(context: Context, attributeSet: AttributeSet? = null) : ConstraintLayout(context, attributeSet) {

    private val binding = DialogViewBinding.inflate(LayoutInflater.from(context), this, true)

    /*fun setDialogTitle(@StringRes text: Int = R.string.fiscal_regime_title) {
        binding.regimeTv.setText(text)
    }

    fun setDialogContent(@StringRes text: Int = R.string.fiscal_regime_content) {
        binding.regimeMessage.setText(text)
    }*/

    @RequiresApi(Build.VERSION_CODES.M)
    fun setTitleStyle(@StyleRes style: Int) {
        binding.regimeTv.setTextAppearance(style)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setContentStyle(@StyleRes style: Int) {
        binding.regimeMessage.setTextAppearance(style)
    }

    fun setInvisible() {
        binding.regimeOkButton.setOnClickListener {
            binding.root.visibility = View.GONE
        }
    }

}