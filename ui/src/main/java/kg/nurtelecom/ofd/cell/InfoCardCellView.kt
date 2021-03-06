package kg.nurtelecom.ofd.cell

import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.card.MaterialCardView
import kg.nurtelecom.core.extension.visible
import kg.nurtelecom.ui.R
import kg.nurtelecom.ui.databinding.InfoCardCellViewBinding
import java.math.BigDecimal


class InfoCardCellView(context: Context, attrs: AttributeSet? = null) :
    MaterialCardView(context, attrs) {

    private val vb = InfoCardCellViewBinding.inflate(LayoutInflater.from(context), this, true)
    private var mDisableChildrenTouchEvents: Boolean = false

    init {
        initViewStyle(attrs)
    }

    private fun initViewStyle(attr: AttributeSet?) {
        context.theme.obtainStyledAttributes(attr, R.styleable.InfoCardCellView, 0, 0)
            .apply {
                try {
                    // Set the background color
                    val bgColor = getColor(
                        R.styleable.InfoCardCellView_android_background,
                        Color.WHITE
                    )
                    vb.mcvRoot.setCardBackgroundColor(bgColor)

                    // Set title text
                    val txtColor = getColor(
                        R.styleable.InfoCardCellView_android_textColor,
                        Color.BLACK
                    )
                    val titleText = getString(R.styleable.InfoCardCellView_title_text) ?: ""
                    vb.tvTitle.apply {
                        setTextColor(txtColor)
                        setText(titleText)
                    }

                    // Set edit text
                    val editable = getBoolean(R.styleable.InfoCardCellView_is_editable, true)
                    vb.etContent.setTextColor(txtColor)
                    setIsEditable(editable)

                    // Set the next icon
                    val addNextIcon = getBoolean(R.styleable.InfoCardCellView_add_next_icon, false)
                    if (addNextIcon) {
                        vb.ivNextIcon.visibility = VISIBLE
                    }

                    // Set the hint
                    val hint = getString(R.styleable.InfoCardCellView_hint_text) ?: "0"
                    vb.etContent.hint = hint

                } finally {
                    recycle()
                }
            }
    }

    fun setContent(value: BigDecimal) {
        vb.etContent.setText(value.toPlainString())
    }

    fun getContent(): String {
        return vb.etContent.text.toString()
    }

    fun setHint(hint: BigDecimal) {
        vb.etContent.hint = hint.toString()
    }
    
    fun eraseContent() {
        vb.etContent.setText("")
    }

    fun fetchTextState(action: (text: CharSequence?) -> Unit) {
        vb.etContent.doAfterTextChanged { text ->
            action(text)
        }
    }

    fun fetchInputData(): BigDecimal {
        return if (vb.etContent.text.isNotEmpty()) vb.etContent.text.toString().toBigDecimal()
        else BigDecimal.ZERO
    }

    fun changeEditText(state: Boolean, color: Int = resources.getColor(R.color.green)) {
        when (state) {
            false -> {
                vb.mcvRoot.setCardBackgroundColor(color)
                vb.ivNextIcon.visible(true)
                vb.etContent.setTextColor(Color.WHITE)
                vb.tvTitle.setTextColor(Color.WHITE)
            }
            true -> {
                vb.mcvRoot.setCardBackgroundColor(Color.WHITE)
                vb.ivNextIcon.visible(false)
                vb.etContent.setTextColor(Color.BLACK)
                vb.tvTitle.setTextColor(Color.BLACK)
                eraseContent()
            }
        }
    }
    
    fun setIsEditable(value: Boolean) {
        mDisableChildrenTouchEvents = !value
        if (!value)
            vb.etContent.inputType = InputType.TYPE_NULL
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return mDisableChildrenTouchEvents
    }

}