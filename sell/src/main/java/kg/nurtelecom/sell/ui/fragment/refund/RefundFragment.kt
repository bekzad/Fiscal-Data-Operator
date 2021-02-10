package kg.nurtelecom.sell.ui.fragment.refund

import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.core.ItemClickListener
import kg.nurtelecom.sell.databinding.ChecksHistoryRecycleViewBinding
import kg.nurtelecom.sell.ui.fragment.history.HistoryAdapter
import kg.nurtelecom.sell.ui.fragment.history.HistoryViewModel
import kg.nurtelecom.sell.ui.fragment.refund.detail.RefundDetailFragment
import kg.nurtelecom.sell.utils.doOnMenuItemCollapse
import kg.nurtelecom.sell.utils.doOnQueryTextChange

class RefundFragment : CoreFragment<ChecksHistoryRecycleViewBinding, HistoryViewModel>(HistoryViewModel::class), ItemClickListener {

    private var historyAdapter: HistoryAdapter = HistoryAdapter(this)

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ChecksHistoryRecycleViewBinding {
        return ChecksHistoryRecycleViewBinding.inflate(layoutInflater)
    }

    override fun setupToolbar(): Int  = R.string.text_return

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

    override fun removeProduct(position: Int) {
        vm.fetchDetailCheckHistory(position)
        val checkId = bundleOf(CHECK_ID to position)
        parentActivity.replaceFragment<RefundDetailFragment>(R.id.sell_container) {
            checkId
        }
    }

    override fun setupViews() {
        setHasOptionsMenu(true)
        initRecyclerView()
        vm.fetchChecksHistory()
    }

    override fun subscribeToLiveData() {
        vm.checksHistoryData.observe(viewLifecycleOwner, { items ->
            historyAdapter.addHeaderAndSubmitList(items.filter { it.operationType == "SALE" })
        })
        vm.filteredChecksHistory?.observe(viewLifecycleOwner) { sortedItems ->
            historyAdapter.addHeaderAndSubmitList(null, sortedList = sortedItems.filter { it.operationType == "SALE" })
        }
    }

    private fun initRecyclerView() {
        vb.rvHistory.adapter = historyAdapter
    }

    companion object {
        fun newInstance(): RefundFragment {
            return RefundFragment()
        }
        const val CHECK_ID: String = "check_id"
    }
}