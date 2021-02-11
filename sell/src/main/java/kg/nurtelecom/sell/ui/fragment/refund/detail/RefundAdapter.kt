package kg.nurtelecom.sell.ui.fragment.refund.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.nurtelecom.core.extension.roundOff
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
            val discount = if (item.discount.toInt() != 0) "- ${item.discount.toInt()}%" else ""
            val charge = if (item.charge.toInt() != 0) "+ ${item.charge.toInt()}%" else ""
            binding.apply {
                tvTitle.text = item.productName
                tvSubTitle.text = "${item.productUnitPrice.roundOff(2)} * ${item.productQuantity} $discount $charge"
                tvCellValue.text = "${item.total.roundOff(2)} с"
                cbSelectItem.visible(true)
            }

            binding.cbSelectItem.setOnCheckedChangeListener { _, isChecked ->
                itemClick.onItemClick(item, isChecked)
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