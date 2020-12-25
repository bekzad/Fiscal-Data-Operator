package kg.nurtelecom.ofd.ui.buttons

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.ui.R
import kg.nurtelecom.ui.databinding.DetailViewBinding

class DetailView(context: Context, attr: AttributeSet) : ConstraintLayout(context, attr) {

    private val vb = DetailViewBinding.inflate(LayoutInflater.from(context), this, true)

    private val saleType: String
    private var counter: String
    private var timestamp: String
    private var amount: String

    init {
        context.theme.obtainStyledAttributes(
            attr,
            R.styleable.custom_view, 0, 0).apply {
                try {
                    saleType = getString(R.styleable.custom_view_saleType).toString()
                    counter = getString(R.styleable.custom_view_counter).toString()
                    timestamp = getString(R.styleable.custom_view_timestamp).toString()
                    amount = getString(R.styleable.custom_view_amount).toString()
                    setupViews()
                } finally {
                    recycle()
                }
            }
    }

    private fun setupViews() {
        vb.tvTitleDetailSaleType.text = saleType
        vb.tvDetailCounter.text = counter
        vb.tvDetailTimestamp.text = timestamp
        vb.tvDetailAmount.text = amount
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        vb.rootLayout.setOnClickListener(l)
    }

    fun setSaleType(text: String) {
        vb.tvTitleDetailSaleType.text = text
    }

    fun setCounter(text: String) {
        vb.tvDetailCounter.text = text
    }

    fun setTimestamp(text: String) {
        vb.tvDetailTimestamp.text = text
    }

    fun setAmount(text: String) {
        vb.tvDetailAmount.text = text
    }

    fun setOnIconClick(action: () -> Unit) {
        vb.detail.setOnClickListener { action() }
    }
}