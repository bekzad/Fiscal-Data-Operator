package kg.nurtelecom.ofd.sell

import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
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
                    vb.twInfoCardTitle.apply {
                        setTextColor(txtColor)
                        setText(titleText)
                    }

                    // Set edit text
                    val editable = getBoolean(R.styleable.InfoCardCellView_is_editable, true)
                    vb.etInfoCardContent.setTextColor(txtColor)
                    vb.etInfoCardContent.isEnabled = editable

                    // Set the next icon
                    val addNextIcon = getBoolean(R.styleable.InfoCardCellView_add_next_icon, false)
                    if (addNextIcon) {
                        vb.ivNextIcon.visibility = VISIBLE
                    }

                    // Set the hint
                    val hint = getString(R.styleable.InfoCardCellView_hint_text) ?: "0"
                    vb.etInfoCardContent.setHint(hint)

                } finally {
                    recycle()
                }
            }
    }

    fun setContent(value: BigDecimal) {
        vb.etInfoCardContent.setText(value.toString())
    }

    fun fetchTextState(action: (text: CharSequence?) -> Unit) {
        vb.etInfoCardContent.doOnTextChanged { text, _, _, _ ->
            action(text)
        }
    }

    fun fetchInputData(): BigDecimal? {
        return vb.etInfoCardContent.text.toString().toBigDecimalOrNull()
    }
}