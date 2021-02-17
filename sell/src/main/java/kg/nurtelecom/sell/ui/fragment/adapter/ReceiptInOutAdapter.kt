package kg.nurtelecom.sell.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.nurtelecom.core.extension.formatForDecoratorDateTimeDefaults
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutHistoryModel
import kg.nurtelecom.sell.databinding.ProductCategoryHeaderBinding
import kg.nurtelecom.ui.databinding.DetailViewBinding
import java.text.SimpleDateFormat
import java.util.*

class ReceiptInOutAdapter(private var dataSource: List<ReceiptInOutHistoryModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val data: MutableList<ListItem> = mutableListOf()

    init {
        filterDataSource()
    }

    fun updateDataSource(newData: List<ReceiptInOutHistoryModel>) {
        dataSource = newData
        filterDataSource()
        notifyDataSetChanged()
    }

    private fun filterDataSource() {
        data.clear()
        val temp = dataSource.groupBy { dateFormat(it.createdAt) }
        temp.forEach {
            data.add(ListItem.Header(dateFormat(it.key)))
            it.value.forEach {
                data.add(ListItem.ReceiptItem(it))
            }
        }
    }

    private fun dateFormat(date: String): String {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSS", Locale.getDefault()).parse(date).formatForDecoratorDateTimeDefaults()
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is ListItem.Header -> HEADER_ITEM_VIEW_TYPE
            is ListItem.ReceiptItem -> RECEIPT_ITEM_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_ITEM_VIEW_TYPE -> HeaderItemViewHolder.newInstance(parent)
            else -> ReceiptItemViewHolder.newInstance(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ReceiptItemViewHolder -> holder.bind(data[position] as ListItem.ReceiptItem)
            is HeaderItemViewHolder -> holder.bind((data[position] as ListItem.Header).name)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }


    class ReceiptItemViewHolder(private val vb: DetailViewBinding) : RecyclerView.ViewHolder(vb.root) {
        fun bind(itemData: ListItem.ReceiptItem) {
            vb.tvTitle.text = itemData.receipt.toString()
        }

        companion object {
            fun newInstance(parent: ViewGroup): ReceiptItemViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val vb = DetailViewBinding.inflate(inflater, parent, false)
                return ReceiptItemViewHolder(vb)
            }
        }
    }

    class HeaderItemViewHolder(private val vb: ProductCategoryHeaderBinding): RecyclerView.ViewHolder(vb.root) {
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

    companion object {
        val RECEIPT_ITEM_VIEW_TYPE = 0
        val HEADER_ITEM_VIEW_TYPE = 1
    }

}

sealed class ListItem {
    data class ReceiptItem(val receipt: ReceiptInOutHistoryModel) : ListItem()
    data class Header(val name: String) : ListItem()
}