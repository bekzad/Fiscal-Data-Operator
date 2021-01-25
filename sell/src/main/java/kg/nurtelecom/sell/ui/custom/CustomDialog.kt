package kg.nurtelecom.sell.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.core.extension.visible
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.DialogViewBinding


class CustomDialog(context: Context, attributeSet: AttributeSet? = null) :
    ConstraintLayout(context, attributeSet) {

    private val binding = DialogViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setupDialog(whichRegime: Boolean) {
        when (whichRegime) {
            true -> setupFiscalDialog(NON_FISCAL_REGIME)
            false -> setupFiscalDialog(FISCAL_REGIME)
        }
    }

    fun hideDialog() {
        binding.tvRegimeOk.setOnClickListener {
            binding.root.visible(false)
        }
    }

    private fun setupFiscalDialog(regime: String) {
        when (regime) {
            FISCAL_REGIME -> {
                setupRegimeContent(R.string.fiscal_mode_title, R.string.fiscal_mode_dialog_content)
            }
            NON_FISCAL_REGIME -> {
                setupRegimeContent(
                    R.string.non_fiscal_mode_message,
                    R.string.non_fiscal_mode_dialog_content
                )
            }
        }
    }

    private fun setupRegimeContent(@StringRes text: Int, @StringRes content: Int) {
        binding.apply {
            tvRegime.setText(text)
            tvRegimeMessage.setText(content)
        }
    }

    companion object {
        private const val FISCAL_REGIME: String = "fiscal"
        private const val NON_FISCAL_REGIME: String = "nonFiscal"
    }
}