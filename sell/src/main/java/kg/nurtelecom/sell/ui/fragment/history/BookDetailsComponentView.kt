package kg.nurtelecom.sell.ui.fragment.history

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import androidx.constraintlayout.widget.ConstraintLayout
import kg.nurtelecom.core.extension.OperationType
import kg.nurtelecom.core.extension.formatForLocalDateTimeDefaults
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.ViewBookDetailsComponentBinding
import kg.nurtelecom.ui.databinding.DetailViewBinding
import java.text.SimpleDateFormat

class BookDetailsComponentView : ConstraintLayout {

    private lateinit var binding: ViewBookDetailsComponentBinding
    private lateinit var bookItemBinding: DetailViewBinding
    private lateinit var adapter: BookAdapter

    var books: List<Content> = emptyList()
        set(value) {
            field = value
            onItemsUpdated()
        }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        binding = ViewBookDetailsComponentBinding.inflate(LayoutInflater.from(context), this, true)
        adapter = BookAdapter(context)
        binding.bookDetailsList.adapter = adapter
    }

    private fun onItemsUpdated() {
        adapter.notifyDataSetChanged()
        binding.bookDetailsList.requestLayoutForChangedDataset()
    }

    inner class BookAdapter(private val context: Context) : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

            val book: Content = books[position]
            var view: View? = convertView

            if (view == null) {
                view = LayoutInflater.from(context)
                    .inflate(R.layout.detail_view, parent, false)
                bookItemBinding = DetailViewBinding.bind(view)
                view.tag = bookItemBinding
            } else {
                bookItemBinding = view.tag as DetailViewBinding
            }

            bookItemBinding.apply {
                tvTitle.text = OperationType.valueOf(book.operationType).type
                tvCounter.text = "#${book.indexNum}"
                tvTimestamp.text = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSS").parse(book.createdAt).formatForLocalDateTimeDefaults()
                tvAmount.text = "${String.format("%.2f", book.total).toDouble()} с"
            }

            return bookItemBinding.root
        }

        override fun getItem(position: Int): Any {
            return books[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            return books.size
        }

        override fun isEnabled(position: Int): Boolean {
            return false
        }
    }
}

fun ListView.requestLayoutForChangedDataset() {

    val listAdapter = this.adapter
    listAdapter?.let { adapter ->
        val itemCount = adapter.count

        var totalHeight = 0
        for (position in 0 until itemCount) {
            val item = adapter.getView(position, null, this)
            item.measure(0, 0)

            totalHeight += item.measuredHeight

            val layoutParams = this.layoutParams
            layoutParams.height = totalHeight
            this.requestLayout()
        }
    }
}