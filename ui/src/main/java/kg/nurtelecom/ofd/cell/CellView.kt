package kg.nurtelecom.ofd.cell

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
            R.styleable.CellView,
            0, 0
        ).apply {
            try {
                setTitle(getString(R.styleable.CellView_title).toString())
                setSubTitle(getString(R.styleable.CellView_subTitle))
                setIconIsVisible(getBoolean(R.styleable.CellView_showArrowIcon, true))
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
        vb.tvTitle.text = titleText
    }

    fun setSubTitle(subTitleText: String?) {
        vb.tvSubTitle.visible(!subTitleText.isNullOrEmpty())
        vb.tvSubTitle.text = subTitleText
    }

    fun setIconIsVisible(value: Boolean) {
        vb.ivArrowIcon.visible(value)
    }

    fun setCellIcon(resId: Int) {
        vb.ivArrowIcon.setImageResource(resId)
    }
}
