package kg.nurtelecom.sell.ui.fragment.refund.detail

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.nurtelecom.data.history_by_id.ReceiptItems
import kg.nurtelecom.ofd.cell.GoodsView
import kg.nurtelecom.ofd.cell.ProductCellView

class RefundAdapter: RecyclerView.Adapter<RefundAdapter.ItemsViewHolder>() {

    var items = ArrayList<ReceiptItems>()

    fun setListData(data: ArrayList<ReceiptItems>) {
        this.items = data
    }

    class ItemsViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val receiptDetailView: GoodsView = view as GoodsView

        fun getCustomView(item: ReceiptItems): GoodsView {
//            Log.d("CHECKED", receiptDetailView.isChecked().toString())
            return receiptDetailView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val itemView = GoodsView(parent.context, null)
        itemView.layoutParams =  ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        return ItemsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        holder.getCustomView(items[position]).setProduct(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}