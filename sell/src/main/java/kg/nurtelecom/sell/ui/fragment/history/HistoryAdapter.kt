package kg.nurtelecom.sell.ui.fragment.history

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.sell.databinding.ChecksHistoryListItemBinding
import android.widget.Filter
import android.widget.Filterable
import kg.nurtelecom.data.enums.OperationType
import java.util.*
import kotlin.collections.ArrayList

class HistoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var mFilteredList: MutableList<Content>? = null
    var contents = ArrayList<Content>()

    fun setListData(data: ArrayList<Content>) {
        this.contents = data
    }


    private var itemHeaders: List<String> = listOf()

    var itemData: Map<String, List<Content>> = emptyMap()
        set(value) {
            field = value
            itemHeaders = itemData.keys.toList()
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val viewBinding: ChecksHistoryListItemBinding =
            ChecksHistoryListItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position >= 0 && position < itemHeaders.size) {
            (holder as ItemViewHolder).bind(itemHeaders[position])
        }
    }

    override fun getItemCount() = itemHeaders.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    mFilteredList = contents
                } else {
                    val filteredList = ArrayList<Content>()
                    for (androidVersion in contents) {
                        if (charString in OperationType.valueOf(androidVersion.operationType).type.toLowerCase(Locale.ROOT)) {
                            filteredList.add(androidVersion)
                        }
                    }
                    mFilteredList = filteredList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = mFilteredList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                mFilteredList = filterResults.values as MutableList<Content>
                notifyDataSetChanged()
            }
        }
    }


    inner class ItemViewHolder(private val viewBinding: ChecksHistoryListItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(header: String) {
            viewBinding.tvHeader.text = header
            itemData[header]?.let { items ->
                viewBinding.lvItemDetailView.items = items
                viewBinding.root
            }
        }
    }
}