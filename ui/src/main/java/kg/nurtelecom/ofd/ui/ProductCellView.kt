package kg.nurtelecom.ofd.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CompoundButton
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.core.extension.visible
import kg.nurtelecom.ui.R
import kg.nurtelecom.ui.databinding.ProductCellViewBinding

class ProductCellView(context: Context, attr: AttributeSet) : ConstraintLayout(context, attr) {
    private val vb = ProductCellViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.theme.obtainStyledAttributes(
            attr,
            R.styleable.custom_view,
            0, 0).apply {
            try {
                setTitle(getString(R.styleable.custom_view_title).toString())
                setSubTitle(getString(R.styleable.custom_view_subTitle))
                setValueText(getString(R.styleable.custom_view_value))
                vb.cbProductCellView.visible(getBoolean(R.styleable.custom_view_showCheckBox, false))
                vb.ivProductCellLeftIcon.visible(getBoolean(R.styleable.custom_view_showLeftIcon, false))
                vb.ivProductCellRightIcon.visible(getBoolean(R.styleable.custom_view_showRightIcon, false))
            } finally {
                recycle()
            }
        }
    }

    fun setTitle(title: String) {
        vb.tvProductCellTitle.text = title
    }

    fun setSubTitle(subTitleText: String?) {
        vb.tvProductCellSubTitle.visible(!subTitleText.isNullOrEmpty())
        vb.tvProductCellSubTitle.text = subTitleText
    }

    fun setValueText(text: String?) {
        vb.tvProductCellValueText.visible(!text.isNullOrEmpty())
        vb.tvProductCellValueText.text = text
    }

    fun setOnCheckedChangeListener(listener: CompoundButton.OnCheckedChangeListener) {
        vb.cbProductCellView.visible(true)
        vb.cbProductCellView.setOnCheckedChangeListener(listener)
    }

    fun setOnLeftIconClickListener(listener: OnClickListener) {
        vb.ivProductCellLeftIcon.visible(true)
        vb.ivProductCellLeftIcon.setOnClickListener(listener)
    }

    fun setOnCellClickListener(listener: OnClickListener) {
        vb.root.setOnClickListener(listener)
    }

    fun setLeftIconImageResource(resId: Int) {
        vb.ivProductCellRightIcon.visible(true)
        vb.ivProductCellRightIcon.setImageResource(resId)
    }
}