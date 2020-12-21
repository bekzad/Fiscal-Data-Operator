package kg.nurtelecom.ofd.ui.buttons

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.ui.R
import kg.nurtelecom.ui.databinding.CellButtonBottomBinding
import kg.nurtelecom.ui.databinding.CellButtonTopBinding
import kg.nurtelecom.ui.databinding.DetalBottomBinding
import kg.nurtelecom.ui.databinding.DetalTopBinding


class DetalBottomView(context: Context, attr: AttributeSet) : ConstraintLayout(context, attr) {

    private val binding by lazy {
        DetalBottomBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.detal_bottom, this, true)

    }

    fun setSaleType(text: String) {
        binding.tvTitleDetalBottomSaleType.text = text
    }

    fun setCounter(text: String) {
        binding.tvDetalBottomCounter.text = text
    }

    fun setTimestamp(text: String) {
        binding.tvDetalBottomTimestamp.text = text
    }

    fun setAmount(text: String) {
        binding.tvDetalBottomAmount.text = text
    }

    fun setOnIconClick(action: () -> Unit) {
        binding.detalBottom.setOnClickListener { action() }
    }
}