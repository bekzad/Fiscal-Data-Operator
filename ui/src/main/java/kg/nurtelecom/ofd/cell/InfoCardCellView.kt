package kg.nurtelecom.ofd.cell

import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.widget.doOnTextChanged
import com.google.android.material.card.MaterialCardView
import kg.nurtelecom.ui.R
import kg.nurtelecom.ui.databinding.InfoCardCellViewBinding
import java.math.BigDecimal

class InfoCardCellView(context: Context, attrs: AttributeSet? = null) :
    MaterialCardView(context, attrs) {

    private val vb = InfoCardCellViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        initViewStyle(attrs)
    }

    private fun initViewStyle(attr: AttributeSet?) {
        context.theme.obtainStyledAttributes(attr, R.styleable.InfoCardCellView, 0, 0)
            .apply {
                try {
                    // Set the background color
                    val bgColor = getColor(R.styleable.InfoCardCellView_android_background, Color.WHITE)
                    vb.mcvRoot.setCardBackgroundColor(bgColor)

                    // Set title text
                    val txtColor = getColor(R.styleable.InfoCardCellView_android_textColor, Color.BLACK)
                    val titleText = getString(R.styleable.InfoCardCellView_title_text) ?: ""
                    vb.tvTitle.apply {
                        setTextColor(txtColor)
                        setText(titleText)
                    }

                    // Set edit text
                    val editable = getBoolean(R.styleable.InfoCardCellView_is_editable, true)
                    vb.etContent.setTextColor(txtColor)
                    if (!editable) {
                        vb.etContent.inputType = InputType.TYPE_NULL
                    }

                    // Set the next icon
                    val addNextIcon = getBoolean(R.styleable.InfoCardCellView_add_next_icon, false)
                    if (addNextIcon) {
                        vb.ivNextIcon.visibility = VISIBLE
                    }

                    // Set the hint
                    val hint = getString(R.styleable.InfoCardCellView_hint_text) ?: "0"
                    vb.etContent.setHint(hint)

                } finally {
                    recycle()
                }
            }
    }

    fun setContent(value: BigDecimal) {
        vb.etContent.setText(value.toString())
    }

    fun fetchTextState(action: (text: CharSequence?) -> Unit) {
        vb.etContent.doOnTextChanged { text, _, _, _ ->
            action(text)
        }
    }

    fun fetchInputData(): BigDecimal {
        return if (vb.etContent.text.isNotEmpty()) vb.etContent.text.toString().toBigDecimal()
        else BigDecimal.ZERO
    }
}