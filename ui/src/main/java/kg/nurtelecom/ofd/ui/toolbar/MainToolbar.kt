package kg.nurtelecom.ui.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import kg.nurtelecom.ui.R
import kg.nurtelecom.ui.databinding.MainToolbarBinding

class MainToolbar(context: Context, attr: AttributeSet) : Toolbar(context, attr) {
    val vb = MainToolbarBinding.inflate(LayoutInflater.from(context), this, true)

    private var title: String

    init {
        context.theme.obtainStyledAttributes(attr, R.styleable.custom_view, 0, 0).apply {
            title = getString(R.styleable.custom_view_title).toString()
            vb.tbTitle.text = title
            recycle()
        }

    }


    override fun setTitle(title: CharSequence?) {
        this.title = title as String
        vb.tbTitle.text = title
    }

    override fun setTitle(resId: Int) {
        setTitle(context.getString(resId))
    }
}
