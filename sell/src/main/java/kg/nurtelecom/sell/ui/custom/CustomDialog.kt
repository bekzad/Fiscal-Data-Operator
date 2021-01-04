package kg.nurtelecom.sell.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.DialogViewBinding


class CustomDialog(context: Context, attributeSet: AttributeSet? = null) :
    ConstraintLayout(context, attributeSet) {

    private val binding = DialogViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setupDialog(regime: String) {
        when (regime) {
            FISCAL_REGIME -> setupFiscalDialog(FISCAL_REGIME)
            NON_FISCAL_REGIME -> setupFiscalDialog(NON_FISCAL_REGIME)
            else -> return
        }
    }

    fun hideDialog() {
        binding.regimeOkButton.setOnClickListener {
            binding.root.visibility = View.GONE
        }
    }

    private fun setupFiscalDialog(regime: String) {
        when (regime) {
            FISCAL_REGIME -> {
                binding.apply {
                    regimeTv.setText(R.string.fiscal_mode_title)
                    regimeMessage.setText(R.string.fiscal_mode_dialog_content)
                }
            }
            NON_FISCAL_REGIME -> {
                binding.apply {
                    regimeTv.setText(R.string.non_fiscal_mode_title)
                    regimeMessage.setText(R.string.non_fiscal_mode_dialog_content)
                }
            }
        }
    }

    companion object {
        const val FISCAL_REGIME: String = "fiscal"
        const val NON_FISCAL_REGIME: String = "nonFiscal"
    }
}