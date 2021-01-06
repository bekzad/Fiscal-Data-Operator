package kg.nurtelecom.ofd.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import kg.nurtelecom.ui.R
import kg.nurtelecom.ui.databinding.MainToolbarBinding

class MainToolbar(context: Context, attr: AttributeSet) : Toolbar(context, attr) {
    val vb = MainToolbarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.theme.obtainStyledAttributes(attr, R.styleable.ToolbarView, 0, 0).apply {
            title = getString(R.styleable.ToolbarView_title).toString()
            vb.tbTitle.text = title
            recycle()
        }
    }

    override fun setTitle(title: CharSequence?) {
        vb.mainToolbar.title = title
    }

    override fun setTitle(resId: Int) {
        vb.mainToolbar.setTitle(resId)
    }
}
