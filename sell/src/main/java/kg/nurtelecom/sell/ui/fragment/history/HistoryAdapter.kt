package kg.nurtelecom.sell.ui.fragment.history

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.ofd.ui.buttons.ReceiptDetailView

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    var items = ArrayList<Content>()

    fun setListData(data: ArrayList<Content>) {
        this.items = data
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val receiptDetailView: ReceiptDetailView = view as ReceiptDetailView

        fun getCustomView(): ReceiptDetailView {
            return receiptDetailView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = ReceiptDetailView(parent.context, null)
        itemView.layoutParams =  ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.getCustomView().setReceipt(items[position]);
    }

    override fun getItemCount(): Int {
        return items.size
    }
}