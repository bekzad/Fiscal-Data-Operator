package kg.nurtelecom.ofd.item_decoration

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider

class RoundOutlineProvider(
        var outlineRadius: Float = NONE_ROUNDED_RADIUS,
        var roundMode: RoundMode = RoundMode.NONE
) : ViewOutlineProvider() {

    private val topOffset
        get() = when (roundMode) {
            RoundMode.ALL, RoundMode.TOP -> NONE_ROUNDED_RADIUS.toInt()
            RoundMode.NONE, RoundMode.BOTTOM -> cornerRadius.toInt()
        }
    private val bottomOffset
        get() = when (roundMode) {
            RoundMode.ALL, RoundMode.BOTTOM -> NONE_ROUNDED_RADIUS.toInt()
            RoundMode.NONE, RoundMode.TOP -> cornerRadius.toInt()
        }
    private val cornerRadius
        get() = if (roundMode == RoundMode.NONE) {
            NONE_ROUNDED_RADIUS
        } else {
            outlineRadius
        }

    override fun getOutline(view: View, outline: Outline) {
        outline.setRoundRect(
                0,
                0 - topOffset,
                view.width,
                view.height + bottomOffset,
                cornerRadius
        )
    }

    private companion object {
        private const val NONE_ROUNDED_RADIUS = 0f
    }
}