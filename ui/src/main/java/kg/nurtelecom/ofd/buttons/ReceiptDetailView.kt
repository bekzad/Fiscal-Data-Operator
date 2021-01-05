package kg.nurtelecom.ofd.ui.buttons

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.core.extension.OperationType
import kg.nurtelecom.core.extension.formatForLocalDateTimeDefaults
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.ui.databinding.DetailViewBinding
import java.text.SimpleDateFormat

class ReceiptDetailView(context: Context, attr: AttributeSet?) : ConstraintLayout(context, attr) {

    private val vb = DetailViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setReceipt(content: Content) {
        vb.tvTitle.text = OperationType.valueOf(content.operationType).type
        vb.tvCounter.text = "#${content.indexNum}"
        vb.tvTimestamp.text = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSS").parse(content.createdAt).formatForLocalDateTimeDefaults()
        vb.tvAmount.text = "${String.format("%.2f", content.total).toDouble()} с"
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        vb.rootLayout.setOnClickListener(l)
    }

}