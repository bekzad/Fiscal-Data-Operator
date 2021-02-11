package kg.nurtelecom.sell.ui.fragment.refund.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.nurtelecom.core.extension.visible
import kg.nurtelecom.data.history_by_id.ReceiptItems
import kg.nurtelecom.sell.core.ItemClickListener
import kg.nurtelecom.ui.databinding.ProductCellViewBinding
import java.math.BigDecimal

class RefundAdapter(private val itemClick: ItemClickListener): RecyclerView.Adapter<RefundAdapter.RefundViewHolder>() {

    var items = ArrayList<ReceiptItems>()

    fun setListData(data: ArrayList<ReceiptItems>) {
        this.items = data
    }

    class RefundViewHolder(private val binding: ProductCellViewBinding, private val itemClick: ItemClickListener): RecyclerView.ViewHolder(binding.root){

        fun bind(item: ReceiptItems) {
            binding.apply {
                tvTitle.text = item.productName
                tvSubTitle.text = "${String.format("%.2f", item.productUnitPrice).toDouble()} * ${item.productQuantity} + ${item.discount}"
                tvCellValue.text = "${String.format("%.2f", item.total).toDouble()} с"
                cbSelectItem.visible(true)
            }

            binding.cbSelectItem.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    itemClick.onItemClick(BigDecimal(item.total))
                }
            }
        }
        companion object {
            fun from(parent: ViewGroup, itemClick: ItemClickListener): RefundViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ProductCellViewBinding.inflate(layoutInflater, parent, false)
                return RefundViewHolder(binding, itemClick)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RefundViewHolder {
        return RefundViewHolder.from(parent, itemClick)
    }

    override fun onBindViewHolder(holder: RefundViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}