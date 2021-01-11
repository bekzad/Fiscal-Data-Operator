package kg.nurtelecom.sell.ui.fragment.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.sell.databinding.CheckHistoryListItemBinding

class HistoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemHeaders: List<String> = listOf()

    var itemData: Map<String, List<Content>> = emptyMap()
        set(value) {
            field = value
            itemHeaders = itemData.keys.toList()
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val viewBinding: CheckHistoryListItemBinding =
            CheckHistoryListItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position >= 0 && position < itemHeaders.size) {
            (holder as ItemViewHolder).bind(itemHeaders[position])
        }
    }

    override fun getItemCount() = itemHeaders.size

    fun getHeaderForCurrentPosition(position: Int) = if (position in itemHeaders.indices) {
        itemHeaders[position]
    } else {
        ""
    }

    inner class ItemViewHolder(private val viewBinding: CheckHistoryListItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(header: String) {
            viewBinding.tvHeader.text = header
            itemData[header]?.let { items ->
                viewBinding.itemDetailsView.items = items
            }
        }
    }
}