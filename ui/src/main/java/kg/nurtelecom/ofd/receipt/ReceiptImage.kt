package kg.nurtelecom.ofd.receipt

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import kg.nurtelecom.ui.databinding.ReceiptImageBinding

class ReceiptImage(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {
    private val vb = ReceiptImageBinding.inflate(LayoutInflater.from(context), this, true)
}