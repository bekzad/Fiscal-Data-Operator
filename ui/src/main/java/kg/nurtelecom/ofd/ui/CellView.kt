package kg.nurtelecom.ofd.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.ui.R
import kg.nurtelecom.ui.databinding.CellViewBinding

class CellView(context: Context, attr: AttributeSet) : ConstraintLayout(context, attr) {

    private val vb = CellViewBinding.inflate(LayoutInflater.from(context), this, true)

    private val position: Int
    private var title: String
    private var subTitle: String
    private val showArrowIcon: Boolean
    private val showSubTitle: Boolean

    init {
        context.theme.obtainStyledAttributes(
            attr,
            R.styleable.custom_view,
            0, 0).apply {
            try {
                position = getInteger(R.styleable.custom_view_position, 1)
                title = getString(R.styleable.custom_view_title).toString()
                subTitle = getString(R.styleable.custom_view_subTitle).toString()
                showArrowIcon = getBoolean(R.styleable.custom_view_showArrowIcon, true)
                showSubTitle = getBoolean(R.styleable.custom_view_showSubTitle, true)
                setupViews()
            } finally {
                recycle()
            }
        }
    }

    private fun setupViews() {
        vb.title.text = title
        vb.subTitle.text = subTitle
        if (!showArrowIcon) {
            vb.icon.visibility = View.GONE
        }
        if (!showSubTitle) {
            vb.subTitle.visibility = View.GONE
        }
        when (position) {
            0 -> vb.root.background = context.resources.getDrawable(R.drawable.rounded_top_bg)
            1 -> vb.root.background = context.resources.getDrawable(R.drawable.rounded_mid_bg)
            2 -> vb.root.background = context.resources.getDrawable(R.drawable.rounded_bottom)
            3 -> vb.root.background = context.resources.getDrawable(R.drawable.rounded_single_bg)
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        vb.rootLayout.setOnClickListener(l)
    }

    fun setTitle(value: String) {
        title = value
        vb.title.text = title
    }

    fun setSubtitle(value: String) {
        subTitle = value
        vb.subTitle.text = subTitle
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.i("ERLAN", "onInterceptorTouchEvent")
        return true
    }


}