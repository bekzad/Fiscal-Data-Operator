package kg.nurtelecom.sell.ui.fragment.history

import android.os.Handler
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import kg.nurtelecom.core.CoreEvent
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.ofd.item_decoration.RoundDecor
import kg.nurtelecom.core.extension.setProgressBarColor
import kg.nurtelecom.core.extension.visible
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.core.ItemClickListener
import kg.nurtelecom.sell.databinding.ChecksHistoryRecycleViewBinding
import kg.nurtelecom.sell.ui.activity.SellMainActivity
import kg.nurtelecom.sell.ui.fragment.history.detail.HistoryDetailFragment
import kg.nurtelecom.sell.utils.doOnMenuItemCollapse
import kg.nurtelecom.sell.utils.doOnQueryTextChange

class HistoryFragment : CoreFragment<ChecksHistoryRecycleViewBinding, HistoryViewModel>(HistoryViewModel::class), ItemClickListener {

    private var historyAdapter: HistoryAdapter = HistoryAdapter(this)

    override fun createViewBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ): ChecksHistoryRecycleViewBinding {
        return ChecksHistoryRecycleViewBinding.inflate(layoutInflater)
    }

    override fun setupToolbar(): Int  = R.string.history_title

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.sell_menu, menu)
        val search = menu.findItem(R.id.ic_search)
        val searchView = search.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.queryHint = getString(R.string.text_search)
        search(searchView)
        search.doOnMenuItemCollapse {
            historyAdapter.addHeaderAndSubmitList(vm.checksHistoryData.value, null)
            true
        }
    }

    private fun search(searchView: SearchView) {
        searchView.doOnQueryTextChange { name ->
            vm.searchChecks(name)
            true
        }
    }

    override fun <T> onItemClick(value: T, isChecked: Boolean) {
        vm.fetchDetailCheckHistory(value as Int)
        parentActivity.replaceFragment<HistoryDetailFragment>(R.id.sell_container)
    }

    override fun setupViews() {
        setHasOptionsMenu(true)
        initRecyclerView()
        vm.fetchChecksHistory()
    }

    override fun subscribeToLiveData() {
        vm.event.observe(this, {
            when (it) {
                is CoreEvent.Loading -> {
                    (activity as SellMainActivity?)?.isProgressBarVisible(true)
                    (activity as SellMainActivity?)?.setProgressBarColor(R.color.green)
                }
                is CoreEvent.Success -> {
                    vm.checksHistoryData.observe(viewLifecycleOwner, { items ->
                        historyAdapter.addHeaderAndSubmitList(items)
                    })
                    vm.filteredChecksHistory?.observe(viewLifecycleOwner) { sortedItems ->
                        historyAdapter.addHeaderAndSubmitList(null, sortedList = sortedItems)
                    }
                    (activity as SellMainActivity?)?.isProgressBarVisible(false)
                }
                is CoreEvent.Error -> {
                    (activity as SellMainActivity?)?.setProgressBarColor(R.color.red)
                    Handler().postDelayed({
                        (activity as SellMainActivity?)?.isProgressBarVisible(false)
                    }, 1000)
                }
            }
        })
    }

    private fun initRecyclerView() {
        vb.rvHistory.adapter = historyAdapter
        vb.rvHistory.addItemDecoration(RoundDecor())
    }

    companion object {
        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }
}

