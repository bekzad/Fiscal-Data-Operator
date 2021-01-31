package kg.nurtelecom.sell.ui.fragment.credit

import android.graphics.Color
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.CreditListFragmentBinding
import kg.nurtelecom.sell.ui.fragment.history.HistoryAdapter
import kg.nurtelecom.sell.core.CoreFragment


class CreditListFragment : CoreFragment<CreditListFragmentBinding>(){

    private var historyAdapter: HistoryAdapter = HistoryAdapter()

    override val vm: CreditListVM by activityViewModels()

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        observeCreditList()
    }

    override fun setupViews() {
        super.setupViews()
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): CreditListFragmentBinding {
        return CreditListFragmentBinding.inflate(layoutInflater)
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

//    private fun initRecyclerView() {
//        vb.lvCreditList.adapter = historyAdapter
//    }

    private fun observeCreditList(){

    }

    override fun setupToolbar(): Int = R.string.title_credit_list
}