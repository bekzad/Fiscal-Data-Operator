package kg.nurtelecom.sell.ui.fragment.history.detail

import android.os.Bundle
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.ChecksHistoryDetailFragmentBinding
import kg.nurtelecom.sell.ui.fragment.history.HistoryViewModel

class HistoryDetailFragment : CoreFragment<ChecksHistoryDetailFragmentBinding, HistoryViewModel>(HistoryViewModel::class) {

    override fun onResume() {
        super.onResume()
        parentActivity.setToolbarTitle(R.string.history_title)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val historyId = savedInstanceState?.getInt("key")
        println("History id: $historyId")
    }

    override fun setupViews() {}

    override fun getBinding() = ChecksHistoryDetailFragmentBinding.inflate(layoutInflater)

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        observeCheckHistory()
    }

    // TODO("change fun name to subscribe")
    private fun observeCheckHistory() {}

    companion object {
        fun newInstance(): HistoryDetailFragment {
            return HistoryDetailFragment()
        }
    }
}