package kg.nurtelecom.sell.ui.fragment.refund

import android.graphics.Color
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.core.extension.formatForDecoratorDateTimeDefaults
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.ChecksHistoryRecycleViewBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.fragment.history.HistoryAdapter
import java.text.SimpleDateFormat

class RefundFragment : CoreFragment<ChecksHistoryRecycleViewBinding, SellMainViewModel>(SellMainViewModel::class) {

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
        val searchView = MenuItemCompat.getActionView(search) as SearchView
        searchView.queryHint = getString(R.string.text_search)
        val editText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        editText.setTextColor(Color.WHITE)
        editText.setHintTextColor(Color.WHITE)
        search(searchView)
    }

    private fun search(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean = false
            override fun onQueryTextChange(newText: String): Boolean {
                historyAdapter.filter.filter(newText)
                return true
            }
        })
    }

    override fun setupToolbar(): Int  = R.string.text_return

    override fun setupViews() {
        setHasOptionsMenu(true)
        initRecyclerView()
        vm.fetchChecksHistory()
    }

    private fun initRecyclerView() {
        vb.rvHistory.adapter = historyAdapter
    }

    override fun subscribeToLiveData() {
        observeCheckHistory()
    }

    private fun observeCheckHistory() {
        vm.checksHistoryData.observe(this, {
            if (it != null) {
                val groupedItems = it.groupBy { book ->
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSS").parse(book.createdAt).formatForDecoratorDateTimeDefaults()
                }
                historyAdapter.itemData = groupedItems.toSortedMap()
                historyAdapter.setListData(it as ArrayList<Content>)
                historyAdapter.notifyDataSetChanged()
            }
        })
    }
    companion object {
        fun newInstance(): RefundFragment {
            return RefundFragment()
        }
    }
}