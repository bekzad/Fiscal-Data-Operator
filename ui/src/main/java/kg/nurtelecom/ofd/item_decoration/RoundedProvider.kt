package kg.nurtelecom.ofd.item_decoration

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider

class RoundOutlineProvider(
        var outlineRadius: Float = none_rounded_radius,
        var roundMode: RoundMode = RoundMode.NONE
) : ViewOutlineProvider() {

    private val topOffset
        get() = when (roundMode) {
            RoundMode.ALL, RoundMode.TOP -> none_rounded_radius.toInt()
            RoundMode.NONE, RoundMode.BOTTOM -> cornerRadius.toInt()
        }
    private val bottomOffset
        get() = when (roundMode) {
            RoundMode.ALL, RoundMode.BOTTOM -> none_rounded_radius.toInt()
            RoundMode.NONE, RoundMode.TOP -> cornerRadius.toInt()
        }
    private val cornerRadius
        get() = if (roundMode == RoundMode.NONE) {
            none_rounded_radius
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
        private const val none_rounded_radius = 0f
    }
}