package kg.nurtelecom.sell.ui.fragment.other_operations.prepayment

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.core.ProductItemClickListener
import kg.nurtelecom.sell.databinding.ChecksHistoryRecycleViewBinding
import kg.nurtelecom.sell.ui.fragment.history.HistoryAdapter
import kg.nurtelecom.sell.ui.fragment.history.HistoryViewModel
import kg.nurtelecom.sell.utils.doOnMenuItemCollapse
import kg.nurtelecom.sell.utils.doOnQueryTextChange

class RefundPrepaymentFragment : CoreFragment<ChecksHistoryRecycleViewBinding, HistoryViewModel>(HistoryViewModel::class), ProductItemClickListener {

    private var historyAdapter: HistoryAdapter = HistoryAdapter(this)

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ChecksHistoryRecycleViewBinding {
        return ChecksHistoryRecycleViewBinding.inflate(layoutInflater)
    }

    override fun setupToolbar(): Int  = R.string.text_refund_prepayment

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

    override fun setupViews() {
        setHasOptionsMenu(true)
        initRecyclerView()
        vm.fetchChecksHistory()
    }

    override fun removeProduct(position: Int) {
        vm.fetchDetailCheckHistory(position)
    }

    override fun subscribeToLiveData() {
        vm.checksHistoryData.observe(viewLifecycleOwner, { items ->
            historyAdapter.addHeaderAndSubmitList(items)
        })
        vm.filteredChecksHistory?.observe(viewLifecycleOwner) { sortedItems ->
            historyAdapter.addHeaderAndSubmitList(null, sortedList = sortedItems)
        }
    }

    private fun initRecyclerView() {
        vb.rvHistory.adapter = historyAdapter
    }

    companion object {
        fun newInstance(): RefundPrepaymentFragment {
            return RefundPrepaymentFragment()
        }
    }
}