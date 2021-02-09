package kg.nurtelecom.ofd.cell

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.core.extension.formatForLocalDateTimeDefaults
import kg.nurtelecom.data.enums.OperationType
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.data.history_by_id.ReceiptItems
import kg.nurtelecom.ui.databinding.DetailViewBinding
import kg.nurtelecom.ui.databinding.GoodsViewBinding
import java.text.SimpleDateFormat

class GoodsView(context: Context, attr: AttributeSet?) : ConstraintLayout(context, attr) {

    private val vb = GoodsViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setProduct(item: ReceiptItems) {
        vb.tvProductName.text = item.productName
        vb.tvCounter.text = "${String.format("%.2f", item.productUnitPrice).toDouble()} * ${item.productQuantity} + ${item.discount}"
        vb.tvProductPrice.text = "${String.format("%.2f", item.total).toDouble()} с"
    }

    fun isChecked(): Boolean {
        return vb.cbSelectItem.isChecked
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        vb.rootLayout.setOnClickListener(l)
    }
}