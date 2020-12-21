package kg.nurtelecom.ofd.ui.buttons

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.ui.R
import kg.nurtelecom.ui.databinding.CellButtonBottomBinding
import kg.nurtelecom.ui.databinding.CellButtonTopBinding
import kg.nurtelecom.ui.databinding.DetalMidBinding
import kg.nurtelecom.ui.databinding.DetalTopBinding


class DetalMidView(context: Context, attr: AttributeSet) : ConstraintLayout(context, attr) {

    private val binding by lazy {
        DetalMidBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.detal_mid, this, true)

    }

    fun setSaleType(text: String) {
        binding.tvTitleDetalMidSaleType.text = text
    }

    fun setCounter(text: String) {
        binding.tvDetalMidCounter.text = text
    }

    fun setTimestamp(text: String) {
        binding.tvDetalMidTimestamp.text = text
    }

    fun setAmount(text: String) {
        binding.tvDetalMidAmount.text = text
    }

    fun setOnIconClick(action: () -> Unit) {
        binding.detalMid.setOnClickListener { action() }
    }
}