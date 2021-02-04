package kg.nurtelecom.sell.ui.fragment.history

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import kg.nurtelecom.core.extension.formatForDecoratorDateTimeDefaults
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.ChecksHistoryRecycleViewBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.utils.doOnQueryTextChange
import java.text.SimpleDateFormat
import java.util.*

class HistoryFragment : CoreFragment<ChecksHistoryRecycleViewBinding, SellMainViewModel>(SellMainViewModel::class) {

    private var historyAdapter: HistoryAdapter = HistoryAdapter()

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
        val searchView = search.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.queryHint = getString(R.string.text_search)
        search(searchView)
    }

    override fun setupToolbar(): Int  = R.string.history_title

    override fun setupViews() {
        setHasOptionsMenu(true)
        initRecyclerView()
        vm.fetchChecksHistory()
    }

    override fun subscribeToLiveData() {
        observeCheckHistory()
    }

    private fun search(searchView: SearchView) {
        searchView.doOnQueryTextChange { newText ->
            historyAdapter.filter.filter(newText)
            true
        }
    }

    private fun initRecyclerView() {
        vb.rvHistory.adapter = historyAdapter
    }

    private fun observeCheckHistory() {
        vm.checksHistoryData.observe(viewLifecycleOwner, {
            if (it != null) {
                val groupedItems = it.groupBy { book ->
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSS", Locale.getDefault()).parse(book.createdAt).formatForDecoratorDateTimeDefaults()
                }
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