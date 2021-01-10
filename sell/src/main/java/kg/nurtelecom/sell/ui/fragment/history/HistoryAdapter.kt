package kg.nurtelecom.sell.ui.fragment.history

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.ofd.buttons.ReceiptDetailView

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.ItemsViewHolder>() {

    var items = ArrayList<Content>()

    fun setListData(data: ArrayList<Content>) {
        this.items = data
    }

    fun getHeaderForCurrentPosition(position: Int) = if (position in items.indices) {
        items[position].createdAt
    } else {
        ""
    }

    class ItemsViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val receiptDetailView: ReceiptDetailView = view as ReceiptDetailView

        fun getCustomView(item: Content): ReceiptDetailView {
            itemView.setOnClickListener {
                Log.d("ITEM", item.id.toString())
//                val activity = v.context as AppCompatActivity
//                activity.replaceFragment(
//                    R.id.history_recycler_view,
//                    HistoryDetailFragment.newInstance()
//                )
            }
            return receiptDetailView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val itemView = ReceiptDetailView(parent.context, null)
        itemView.layoutParams =  ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        return ItemsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        holder.getCustomView(items[position]).setReceipt(items[position]);
    }

    override fun getItemCount(): Int {
        return items.size
    }
}