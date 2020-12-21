package kg.nurtelecom.ofd.ui.buttons

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.ui.R
import kg.nurtelecom.ui.databinding.DetalTopBinding


class DetalTopView(context: Context, attr: AttributeSet) : ConstraintLayout(context, attr) {

    private val binding by lazy {
        DetalTopBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.detal_top, this, true)

    }

    fun setSaleType(text: String) {
        binding.tvTitleDetalTopSaleType.text = text
    }

    fun setCounter(text: String) {
        binding.tvDetalTopCounter.text = text
    }

    fun setTimestamp(text: String) {
        binding.tvDetalTopTimestamp.text = text
    }

    fun setAmount(text: String) {
        binding.tvDetalTopAmount.text = text
    }

    fun setOnIconClick(action: () -> Unit) {
        binding.detalTop.setOnClickListener { action() }
    }
}
