package kg.nurtelecom.ofd.item_decoration

import android.graphics.Canvas
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

class RoundDecor(
        private val cornerRadius: Float = 20F
) : RecyclerView.ItemDecoration() {

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        parent.children.forEach {
            draw(it, parent)
        }
    }

    private fun draw(
            view: View,
            recyclerView: RecyclerView,
    ) {

        val viewHolder = recyclerView.getChildViewHolder(view)
        val nextViewHolder = recyclerView.findViewHolderForAdapterPosition(viewHolder.adapterPosition + 1)
        val previousChildViewHolder = recyclerView.findViewHolderForAdapterPosition(viewHolder.adapterPosition - 1)

        if (cornerRadius.compareTo(0f) != 0) {
            val roundMode = getRoundMode(previousChildViewHolder, viewHolder, nextViewHolder)
            val outlineProvider = view.outlineProvider
            if (outlineProvider is RoundOutlineProvider) {
                outlineProvider.roundMode = roundMode
                view.invalidateOutline()
            } else {
                view.outlineProvider = RoundOutlineProvider(cornerRadius, roundMode)
                view.clipToOutline = true
            }
        }
    }

    private fun getRoundMode(
            previousChildViewHolder: RecyclerView.ViewHolder?,
            currentViewHolder: RecyclerView.ViewHolder?,
            nextChildViewHolder: RecyclerView.ViewHolder?
    ): RoundMode {

        val previousHolderItemType = previousChildViewHolder?.itemViewType
        val currentHolderItemType = currentViewHolder?.itemViewType
        val nextHolderItemType = nextChildViewHolder?.itemViewType

        return when {
            previousHolderItemType != currentHolderItemType && currentHolderItemType != nextHolderItemType -> RoundMode.ALL
            previousHolderItemType != currentHolderItemType && currentHolderItemType == nextHolderItemType -> RoundMode.TOP
            previousHolderItemType == currentHolderItemType && currentHolderItemType != nextHolderItemType -> RoundMode.BOTTOM
            previousHolderItemType == currentHolderItemType && currentHolderItemType == nextHolderItemType -> RoundMode.NONE
            else -> RoundMode.NONE
        }
    }
}

enum class RoundMode {
    TOP,
    BOTTOM,
    ALL,
    NONE
}