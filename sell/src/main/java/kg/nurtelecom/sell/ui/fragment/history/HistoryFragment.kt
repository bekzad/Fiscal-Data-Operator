package kg.nurtelecom.sell.ui.fragment.history

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kg.nurtelecom.core.extension.formatForLocalDateTimeDefaults
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.HistoryRecycleViewBinding
import java.text.SimpleDateFormat

class HistoryFragment : CoreFragment<HistoryRecycleViewBinding, HistoryViewModel>(HistoryViewModel::class) {

    private var historyAdapter: HistoryAdapterFake = HistoryAdapterFake()

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).setToolbarTitle(R.string.history_title)
    }

    override fun setupViews() {
        initRecyclerView()
        vm.fetchChecksHistory()
    }

    private fun initRecyclerView() {
        vb.historyRecyclerView.apply {

//            addItemDecoration(
//                StickyHeaderDateDecoration(historyAdapter, vb.root)
//            )

//            layoutManager = LinearLayoutManager(activity)
//            adapter = historyAdapter
        }
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        observeCheckHistory()
    }

    private fun observeCheckHistory() {
        vm.checksHistoryData.observe(this, Observer {
            if(it != null) {
                val items = it
                val groupedBooks = items.groupBy { book -> SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSS").parse(book.createdAt).formatForLocalDateTimeDefaults().last().toUpperCase() }
                historyAdapter.bookData = groupedBooks.toSortedMap()

                vb.historyRecyclerView.addItemDecoration(
                    StickyHeaderDateDecoration(historyAdapter, vb.root)
                )
                vb.historyRecyclerView.layoutManager = LinearLayoutManager(activity)
                vb.historyRecyclerView.adapter = historyAdapter
            }
        })
    }

    override fun getBinding() = HistoryRecycleViewBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }
}