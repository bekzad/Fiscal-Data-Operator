package kg.nurtelecom.sell.ui.fragment.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.sell.databinding.CheckHistoryListItemBinding

class HistoryAdapterFake : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var bookHeaders: List<Char> = listOf()

    var bookData: Map<Char, List<Content>> = emptyMap()
        set(value) {
            field = value
            bookHeaders = bookData.keys.toList()
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val viewBinding: CheckHistoryListItemBinding =
            CheckHistoryListItemBinding.inflate(layoutInflater, parent, false)
        return BookViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position >= 0 && position < bookHeaders.size) {
            (holder as BookViewHolder).bind(bookHeaders[position])
        }
    }

    override fun getItemCount() = bookHeaders.size

    fun getHeaderForCurrentPosition(position: Int) = if (position in bookHeaders.indices) {
        bookHeaders[position]
    } else {
        ""
    }

    inner class BookViewHolder(private val viewBinding: CheckHistoryListItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(header: Char) {
            viewBinding.tvHeader.text = "$header"
            bookData[header]?.let { books ->
                viewBinding.bookDetailsView.books = books
            }
        }
    }
}