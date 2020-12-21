package kg.nurtelecom.ofd.ui.buttons

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.ui.R
import kg.nurtelecom.ui.databinding.CellButtonTopBinding


class ButtonTopView(context: Context, attr: AttributeSet) : ConstraintLayout(context, attr) {

    private val binding by lazy {
        CellButtonTopBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.cell_button_top, this, true)

    }

    fun setTitle(text: String) {
        binding.tvTitle.text = text
    }

    fun setValue(text: String) {
        binding.tvSubtitle.text = text
    }

    fun setOnIconClick(action: () -> Unit) {
        binding.ivIcon.setOnClickListener { action() }
    }
}
