package kg.nurtelecom.sell.ui.fragment.credit

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.core.ItemClickListener
import kg.nurtelecom.sell.databinding.CreditListFragmentBinding
import kg.nurtelecom.sell.ui.fragment.history.HistoryAdapter
import kg.nurtelecom.sell.ui.fragment.history.HistoryViewModel
import kg.nurtelecom.sell.ui.fragment.refund.RefundFragment.Companion.CHECK_ID
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

    override fun <T> onItemClick(value: T, isChecked: Boolean) {
        vm.fetchDetailCheckHistory(value as Int)
        val checkId = bundleOf(CHECK_ID to value)
        parentActivity.replaceFragment<CreditCheckViewFragment>(R.id.sell_container) {
            checkId
        }
    }


    override fun subscribeToLiveData() {
        vm.checksHistoryData.observe(viewLifecycleOwner, { product ->
            historyAdapter.addHeaderAndSubmitList(product.filter { it.operationType == "POSTPAY" })
        })
        vm.filteredChecksHistory?.observe(viewLifecycleOwner) { sortedProducts ->
            historyAdapter.addHeaderAndSubmitList(null, sortedList = sortedProducts.filter { it.operationType == "POSTPAY" })
        }
    }

    private fun initRecyclerView() {
        vb.lvCreditList.adapter = historyAdapter
    }

    override fun setupToolbar(): Int = R.string.title_credit_list

    override fun createViewBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ): CreditListFragmentBinding {
        return CreditListFragmentBinding.inflate(layoutInflater)
    }

    companion object {
        fun newInstance(): CreditListFragment {
            return CreditListFragment()
        }
    }
}