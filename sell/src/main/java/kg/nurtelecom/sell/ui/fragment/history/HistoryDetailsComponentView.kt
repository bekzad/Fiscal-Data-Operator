package kg.nurtelecom.sell.ui.fragment.history

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.core.extension.requestLayoutForChangedDataset
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.ofd.cell.ReceiptDetailView
import kg.nurtelecom.sell.databinding.ChecksHistoryDetailsComponentViewBinding

class HistoryDetailsComponentView(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    private lateinit var binding: ChecksHistoryDetailsComponentViewBinding
    private lateinit var adapter: ItemAdapter

    var items: List<Content> = emptyList()
        set(value) {
            field = value
            onItemsUpdated()
        }

    init {
        init(context)
    }

    private fun init(context: Context) {
        binding = ChecksHistoryDetailsComponentViewBinding.inflate(LayoutInflater.from(context), this, true)
        adapter = ItemAdapter(context)
        binding.checksHistoryDetailsList.adapter = adapter
    }

    private fun onItemsUpdated() {
        adapter.notifyDataSetChanged()
        binding.checksHistoryDetailsList.requestLayoutForChangedDataset()
    }

    inner class ItemAdapter(private val context: Context) : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

            val item: Content = items[position]
            val itemView = parent?.let { ReceiptDetailView(it.context, null) }
            if (itemView != null) {
                itemView.layoutParams =  ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            }

            itemView?.setReceipt(item)
            return itemView
        }

        override fun getItem(position: Int): Any {
            return items[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            return items.size
        }

        override fun isEnabled(position: Int): Boolean {
            return false
        }
    }
}