package kg.nurtelecom.ofd.ui.buttons

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.ui.R
import kg.nurtelecom.ui.databinding.CellButtonBottomBinding
import kg.nurtelecom.ui.databinding.CellButtonSingleBinding


class ButtonSingleView(context: Context, attr: AttributeSet) : ConstraintLayout(context, attr) {

    private val binding by lazy {
        CellButtonSingleBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.cell_button_single, this, true)

    }

    fun setTitle(text: String) {
        binding.tvTitleSingle.text = text
    }

    fun setOnIconClick(action: () -> Unit) {
        binding.ivIconSingle.setOnClickListener { action() }
    }
}