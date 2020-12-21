package kg.nurtelecom.ofd.ui.buttons

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.ui.R
import kg.nurtelecom.ui.databinding.CellButtonBottomBinding


class ButtonBottomView(context: Context, attr: AttributeSet) : ConstraintLayout(context, attr) {

    private val binding by lazy {
        CellButtonBottomBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.cell_button_bottom, this, true)

    }

    fun setTitle(text: String) {
        binding.tvTitleBottom.text = text
    }

    fun setValue(text: String) {
        binding.tvSubtitleBottom.text = text
    }

    fun setOnIconClick(action: () -> Unit) {
        binding.ivIconBottom.setOnClickListener { action() }
    }
}
