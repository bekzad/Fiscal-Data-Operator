package kg.nurtelecom.sell.ui.fragment.credit

import android.view.Menu
import android.view.MenuInflater
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.ItemClickListener
import kg.nurtelecom.sell.databinding.CreditListFragmentBinding
import kg.nurtelecom.sell.ui.fragment.history.HistoryAdapter
import kg.nurtelecom.sell.ui.fragment.history.HistoryViewModel
import kg.nurtelecom.sell.utils.doOnMenuItemCollapse
import kg.nurtelecom.sell.utils.doOnQueryTextChange

class CreditListFragment : CoreFragment<CreditListFragmentBinding, HistoryViewModel>(HistoryViewModel::class), ItemClickListener {

    private var historyAdapter: HistoryAdapter = HistoryAdapter(this)

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

    override fun removeProduct(id: Int) {
        vm.fetchDetailCheckHistory(id)
    }


    override fun subscribeToLiveData() {
        vm.checksHistoryData.observe(viewLifecycleOwner, { product ->
            historyAdapter.addHeaderAndSubmitList(product)
        })
        vm.filteredChecksHistory?.observe(viewLifecycleOwner) { sortedProducts ->
            historyAdapter.addHeaderAndSubmitList(null, sortedList = sortedProducts)
        }
    }

    private fun initRecyclerView() {
        vb.lvCreditList.adapter = historyAdapter
    }

    fun setupToolbar(): Int = R.string.title_credit_list

    override fun getBinding(): CreditListFragmentBinding {
        return CreditListFragmentBinding.inflate(layoutInflater)
    }

    companion object {
        fun newInstance(): CreditListFragment {
            return CreditListFragment()
        }
    }
}