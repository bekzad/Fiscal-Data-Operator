package kg.nurtelecom.ofd.ui.buttons

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.ui.databinding.DetailViewBinding

class ReceiptDetailView(context: Context, attr: AttributeSet?) : ConstraintLayout(context, attr) {

    private val vb = DetailViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setReceipt(content: Content) {
        vb.tvTitle.text = content.operationType
        vb.tvCounter.text = "#${content.indexNum}"
        vb.tvTimestamp.text = content.createdAt
        vb.tvAmount.text = "${content.total} с"
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        vb.rootLayout.setOnClickListener(l)
    }

}