package kg.nurtelecom.ofd.ui.buttons

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.ui.databinding.DetailViewBinding

class ReceiptDetailView(context: Context, attr: AttributeSet) : ConstraintLayout(context, attr) {

    private val vb = DetailViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setReceipt(saleType: String, counter: String, timestamp: String, amount: String) {
        vb.tvTitle.text = saleType
        vb.tvCounter.text = counter
        vb.tvTimestamp.text = timestamp
        vb.tvAmount.text = amount
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        vb.rootLayout.setOnClickListener(l)
    }

}