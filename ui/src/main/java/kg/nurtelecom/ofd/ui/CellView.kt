package kg.nurtelecom.ofd.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.core.extension.visible
import kg.nurtelecom.ui.R
import kg.nurtelecom.ui.databinding.CellViewBinding

class CellView(context: Context, attr: AttributeSet) : ConstraintLayout(context, attr) {

    private val vb = CellViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.theme.obtainStyledAttributes(
            attr,
            R.styleable.custom_view,
            0, 0
        ).apply {
            try {
                setTitle(getString(R.styleable.custom_view_title).toString())
                setSubTitle(getString(R.styleable.custom_view_subTitle))
                setIconIsVisible(getBoolean(R.styleable.custom_view_showArrowIcon, true))
            } finally {
                recycle()
            }
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        vb.rootLayout.setOnClickListener(l)
    }

    fun setTitle(titleText: String) {
        vb.tvCellViewTitle.text = titleText
    }

    fun setSubTitle(subTitleText: String?) {
        vb.tvCellViewSubTitle.visible(!subTitleText.isNullOrEmpty())
        vb.tvCellViewSubTitle.text = subTitleText
    }

    fun setIconIsVisible(value: Boolean) {
        vb.ivCellViewIcon.visible(value)
    }

    fun setCellIcon(resId: Int) {
        vb.ivCellViewIcon.setImageResource(resId)
    }
}
