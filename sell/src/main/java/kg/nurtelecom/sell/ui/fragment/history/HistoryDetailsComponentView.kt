package kg.nurtelecom.sell.ui.fragment.history

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.core.extension.requestLayoutForChangedDataset
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.ofd.cell.ReceiptDetailView
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.ChecksHistoryDetailsComponentViewBinding
import kg.nurtelecom.sell.ui.fragment.history.detail.HistoryDetailFragment

class HistoryDetailsComponentView(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    private var binding: ChecksHistoryDetailsComponentViewBinding =
        ChecksHistoryDetailsComponentViewBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    private var adapter: ItemAdapter = ItemAdapter()
    var items: List<Content> = emptyList()
        set(value) {
            field = value
            onItemsUpdated()
        }

    init {
        binding.lvChecksHistoryDetails.adapter = adapter
    }

    private fun onItemsUpdated() {
        adapter.notifyDataSetChanged()
        binding.lvChecksHistoryDetails.requestLayoutForChangedDataset()
    }

    inner class ItemAdapter : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

            val activity = context as AppCompatActivity
            val item: Content = items[position]
            val itemView = parent?.let { ReceiptDetailView(it.context, null) }
            if (itemView != null) {
                itemView.layoutParams =  ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }

            itemView?.setReceipt(item)
            itemView?.setOnClickListener {
                val checkId = bundleOf(CHECK_ID to item.id)
                activity.replaceFragment<HistoryDetailFragment>(R.id.sell_container) {
                    checkId
                }
            }
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

    companion object {
        const val CHECK_ID: String = "check_id"
    }
}