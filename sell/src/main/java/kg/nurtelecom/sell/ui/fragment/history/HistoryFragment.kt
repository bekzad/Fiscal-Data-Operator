package kg.nurtelecom.sell.ui.fragment.history

import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.core.extension.formatForDecoratorDateTimeDefaults
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.ChecksHistoryRecycleViewBinding
import java.text.SimpleDateFormat

class HistoryFragment : CoreFragment<ChecksHistoryRecycleViewBinding>() {

    private var historyAdapter: HistoryAdapter = HistoryAdapter()

    override val vm: HistoryViewModel by activityViewModels()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ChecksHistoryRecycleViewBinding {
        return ChecksHistoryRecycleViewBinding.inflate(layoutInflater)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.sell_menu, menu)
        val search = menu.findItem(R.id.ic_search)
        val searchView = MenuItemCompat.getActionView(search) as SearchView
        search(searchView)
    }

    private fun search(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                try {
                    historyAdapter.filter.filter(newText)
                    val groupedItems = historyAdapter.mFilteredList?.groupBy { book -> SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSS").parse(book.createdAt).formatForDecoratorDateTimeDefaults() }
                    if (groupedItems != null) {
                        historyAdapter.itemData = groupedItems.toSortedMap()
                    }
                } catch (e: Exception) {
                }
                return true
            }
        })
    }

    override fun setupToolbar(): Int  = R.string.history_title

    override fun setupViews() {
        setHasOptionsMenu(true)
        initRecyclerView()
        vm.fetchChecksHistory()
    }

    private fun initRecyclerView() {
        vb.rvHistory.adapter = historyAdapter
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        observeCheckHistory()
    }

    private fun observeCheckHistory() {
        vm.checksHistoryData.observe(this, {
            if(it != null) {
                val groupedItems = it.groupBy { book -> SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSS").parse(book.createdAt).formatForDecoratorDateTimeDefaults() }
                historyAdapter.itemData = groupedItems.toSortedMap()
                historyAdapter.setListData(it as ArrayList<Content>)
                historyAdapter.notifyDataSetChanged()
            }
        })
    }
    companion object {
        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }
}