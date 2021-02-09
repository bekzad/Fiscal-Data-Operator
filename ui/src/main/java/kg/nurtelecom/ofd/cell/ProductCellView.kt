package kg.nurtelecom.ofd.cell

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CompoundButton
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.core.extension.visible
import kg.nurtelecom.data.history_by_id.ReceiptItems
import kg.nurtelecom.ui.R
import kg.nurtelecom.ui.databinding.ProductCellViewBinding

class ProductCellView(context: Context, attr: AttributeSet) : ConstraintLayout(context, attr) {
    private val vb = ProductCellViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.theme.obtainStyledAttributes(
                attr,
                R.styleable.ProductCellView,
                0, 0).apply {
            try {
                setTitle(getString(R.styleable.ProductCellView_title).toString())
                setSubTitle(getString(R.styleable.ProductCellView_subTitle))
                setCellValue(getString(R.styleable.ProductCellView_value))
                vb.cbSelectItem.visible(getBoolean(R.styleable.ProductCellView_showCheckBox, false))
                vb.ivLeftIcon.visible(getBoolean(R.styleable.ProductCellView_showLeftIcon, false))
                vb.ivRightIcon.visible(getBoolean(R.styleable.ProductCellView_showRightIcon, false))
            } finally {
                recycle()
            }
        }
    }

    fun setTitle(title: String) {
        vb.tvTitle.text = title
    }

    fun setSubTitle(subTitleText: String?) {
        vb.tvSubTitle.visible(!subTitleText.isNullOrEmpty())
        vb.tvSubTitle.text = subTitleText
    }

    fun setCellValue(text: String?) {
        vb.tvCellValue.visible(!text.isNullOrEmpty())
        vb.tvCellValue.text = text
    }

    fun setOnCheckedChangeListener(listener: CompoundButton.OnCheckedChangeListener) {
        vb.cbSelectItem.setOnCheckedChangeListener(listener)
    }

    fun setOnLeftIconClickListener(listener: OnClickListener) {
        vb.ivLeftIcon.setOnClickListener(listener)
    }

    fun setOnCellClickListener(listener: OnClickListener) {
        vb.root.setOnClickListener(listener)
    }

    fun setLeftIconImageResource(resId: Int) {
        vb.ivRightIcon.visible(true)
        vb.ivRightIcon.setImageResource(resId)
    }
}
