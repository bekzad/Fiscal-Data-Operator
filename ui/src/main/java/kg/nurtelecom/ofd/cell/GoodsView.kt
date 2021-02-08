package kg.nurtelecom.ofd.cell

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.core.extension.formatForLocalDateTimeDefaults
import kg.nurtelecom.data.enums.OperationType
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.ui.databinding.DetailViewBinding
import kg.nurtelecom.ui.databinding.GoodsViewBinding
import java.text.SimpleDateFormat

class GoodsView(context: Context, attr: AttributeSet?) : ConstraintLayout(context, attr) {

    private val vb = GoodsViewBinding.inflate(LayoutInflater.from(context), this, true)


    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        vb.rootLayout.setOnClickListener(l)
    }
}