package kg.nurtelecom.sell.ui.fragment.history

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kg.nurtelecom.core.extension.formatForDecoratorDateTimeDefaults
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.HistoryRecycleViewBinding
import java.text.SimpleDateFormat

class HistoryFragment : CoreFragment<HistoryRecycleViewBinding, HistoryViewModel>(HistoryViewModel::class) {

    private var historyAdapter: HistoryAdapter = HistoryAdapter()

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
            vb.historyRecyclerView.layoutManager = LinearLayoutManager(activity)
            vb.historyRecyclerView.adapter = historyAdapter
        }
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        observeCheckHistory()
    }

    private fun observeCheckHistory() {
        vm.checksHistoryData.observe(this, Observer {
            if(it != null) {
                val groupedItems = it.groupBy { book -> SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSS").parse(book.createdAt).formatForDecoratorDateTimeDefaults() }
                historyAdapter.itemData = groupedItems.toSortedMap()
                vb.historyRecyclerView.addItemDecoration(
                    StickyHeaderDateDecoration(historyAdapter, vb.root)
                )
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