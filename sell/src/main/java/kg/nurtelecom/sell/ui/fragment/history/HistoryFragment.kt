package kg.nurtelecom.sell.ui.fragment.history

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.HistoryRecycleViewBinding

class HistoryFragment : CoreFragment<HistoryRecycleViewBinding, HistoryViewModel>(HistoryViewModel::class) {

    private lateinit var historyAdapter: HistoryAdapter

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).setToolbarTitle(R.string.history_title)
    }

    override fun setupViews() {
        initRecyclerView()
        vm.fetchChecksHistory()
    }

    private fun initRecyclerView() {
        vb.historyRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            historyAdapter = HistoryAdapter()
            adapter = historyAdapter
        }
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        observeCheckHistory()
    }

    private fun observeCheckHistory() {
        vm.checksHistoryData.observe(this, Observer {
            if(it != null) {
                historyAdapter.setListData(it as ArrayList<Content>)
                historyAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun getBinding() = HistoryRecycleViewBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }
}
