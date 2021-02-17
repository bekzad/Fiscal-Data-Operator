package kg.nurtelecom.sell.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.nurtelecom.core.extension.formatForDecoratorDateTimeDefaults
import kg.nurtelecom.core.extension.formatForLocalDateTimeDefaults
import kg.nurtelecom.core.extension.roundOff
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutHistoryModel
import kg.nurtelecom.sell.core.ItemClickListener
import kg.nurtelecom.sell.databinding.ProductCategoryHeaderBinding
import kg.nurtelecom.ui.databinding.DetailViewBinding
import java.text.SimpleDateFormat
import java.util.*

class ReceiptInOutAdapter(private val listener: ItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataSource: List<ReceiptInOutHistoryModel> = listOf()
    private val dataSourceFiltered: MutableList<ListItem> = mutableListOf()

    fun updateDataSource(newData: List<ReceiptInOutHistoryModel>) {
        dataSource = newData
        filterDataSource()
    }

    private fun filterDataSource() {
        dataSourceFiltered.clear()
        val temp = dataSource.groupBy { it.createdAt.substring(0..9) }
        temp.forEach {
            dataSourceFiltered.add(ListItem.Header(dateFormat(it.key)))
            it.value.forEach {
                dataSourceFiltered.add(ListItem.ReceiptItem(it))
            }
        }
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {
        return when (dataSourceFiltered[position]) {
            is ListItem.Header -> HEADER_ITEM_VIEW_TYPE
            is ListItem.ReceiptItem -> RECEIPT_ITEM_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_ITEM_VIEW_TYPE -> HeaderItemViewHolder.newInstance(parent)
            else -> ReceiptItemViewHolder.newInstance(parent, listener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ReceiptItemViewHolder -> holder.bind(dataSourceFiltered[position] as ListItem.ReceiptItem)
            is HeaderItemViewHolder -> holder.bind((dataSourceFiltered[position] as ListItem.Header).name)
        }
    }

    override fun getItemCount(): Int {
        return dataSourceFiltered.size
    }


    class ReceiptItemViewHolder(private val vb: DetailViewBinding, private val listener: ItemClickListener) : RecyclerView.ViewHolder(vb.root) {
        fun bind(itemData: ListItem.ReceiptItem) {
            vb.apply {
                tvTitle.text = itemData.receipt.receiptType.type
                tvTimestamp.text = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSS", Locale.getDefault()).parse(itemData.receipt.createdAt).formatForLocalDateTimeDefaults()
                tvAmount.text = "${itemData.receipt.sum.roundOff(2)} c̲"
                tvCounter.text = "#${itemData.receipt.indexNum}"
            }
            vb.root.setOnClickListener { listener.onItemClick(itemData.receipt.id) }
        }

        companion object {
            fun newInstance(parent: ViewGroup, listener: ItemClickListener): ReceiptItemViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val vb = DetailViewBinding.inflate(inflater, parent, false)
                return ReceiptItemViewHolder(vb, listener)
            }
        }
    }

    class HeaderItemViewHolder(private val vb: ProductCategoryHeaderBinding) : RecyclerView.ViewHolder(vb.root) {
        fun bind(header: String) {
            vb.tvHeader.text = header
        }

        companion object {
            fun newInstance(parent: ViewGroup): HeaderItemViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val vb = ProductCategoryHeaderBinding.inflate(inflater, parent, false)
                return HeaderItemViewHolder(vb)
            }
        }
    }

    private fun dateFormat(date: String): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date).formatForDecoratorDateTimeDefaults()
    }

    companion object {
        const val RECEIPT_ITEM_VIEW_TYPE = 0
        const val HEADER_ITEM_VIEW_TYPE = 1
    }

}

sealed class ListItem {
    data class ReceiptItem(val receipt: ReceiptInOutHistoryModel) : ListItem()
    data class Header(val name: String) : ListItem()
}