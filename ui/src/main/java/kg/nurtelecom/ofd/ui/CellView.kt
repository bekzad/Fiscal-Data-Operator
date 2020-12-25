package kg.nurtelecom.ofd.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
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
                vb.title.text = getString(R.styleable.custom_view_title).toString()
                vb.subTitle.text = getString(R.styleable.custom_view_subTitle).toString()
                setIconIsVisible(getBoolean(R.styleable.custom_view_showArrowIcon, true))
                setSubTitleIsVisible(getBoolean(R.styleable.custom_view_showSubTitle, true))
            } finally {
                recycle()
            }
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        //super.setOnClickListener(l)
        vb.rootLayout.setOnClickListener(l)
    }

    fun setTitle(value: String) {
        vb.title.text = value
    }

    fun setSubtitle(value: String) {
        vb.subTitle.text = value
    }

    fun setSubTitleIsVisible(value: Boolean) {
        if (value)
            vb.subTitle.visibility = VISIBLE
        else
            vb.subTitle.visibility = GONE
    }

    fun setIconIsVisible(value: Boolean) {
        if (value)
            vb.cellViewIcon.visibility = VISIBLE
        else
            vb.cellViewIcon.visibility = GONE
    }

    fun setCellIcon(resId: Int) {
        vb.cellViewIcon.setImageResource(resId)
    }

//    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
//        Log.i("ERLAN", "onInterceptorTouchEvent")
//        return true
//    }


}
