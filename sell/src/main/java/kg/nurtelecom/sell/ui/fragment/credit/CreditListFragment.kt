package kg.nurtelecom.sell.ui.fragment.credit

import android.graphics.Color
import android.view.Menu
import android.view.MenuInflater
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import kg.nurtelecom.core.extension.formatForDecoratorDateTimeDefaults
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.CreditListFragmentBinding
import kg.nurtelecom.sell.ui.fragment.history.HistoryAdapter
import java.text.SimpleDateFormat

class CreditListFragment : CoreFragment<CreditListFragmentBinding, CreditListVM>(CreditListVM::class) {

    private var historyAdapter: HistoryAdapter = HistoryAdapter()

    override fun subscribeToLiveData() {
        observeCreditList()
    }

    override fun setupViews() {
        initRecyclerView()
        vm.fetchChecksHistory()
        parentActivity.setToolbarTitle(setupToolbar())
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

    private fun initRecyclerView() {
        vb.lvCreditList.adapter = historyAdapter
    }

    private fun observeCreditList() {
        vm.creditListData.observe(this, {
            if (it != null) {
                val creditList = it.filter { item -> item.operationType == "POSTPAY" }
                historyAdapter.setListData(creditList as ArrayList<Content>)
                val groupedItems = creditList.groupBy { item ->
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSS").parse(item.createdAt)
                        .formatForDecoratorDateTimeDefaults()
                }
                historyAdapter.itemData = groupedItems.toSortedMap()
                historyAdapter.setListData(it as ArrayList<Content>)
                historyAdapter.notifyDataSetChanged()
            }
        })
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