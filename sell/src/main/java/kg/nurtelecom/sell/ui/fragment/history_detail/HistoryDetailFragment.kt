package kg.nurtelecom.sell.ui.fragment.history_detail

import androidx.appcompat.app.AppCompatActivity
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.HistoryDetailFragmentBinding
import kg.nurtelecom.sell.ui.fragment.history.HistoryAdapter
import kg.nurtelecom.sell.ui.fragment.history.HistoryViewModel

class HistoryDetailFragment : CoreFragment<HistoryDetailFragmentBinding, HistoryViewModel>(HistoryViewModel::class) {

    private var historyAdapter1: HistoryAdapter = HistoryAdapter()

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).setToolbarTitle(R.string.history_title)
    }

    override fun setupViews() {
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        observeCheckHistory()
    }

    private fun observeCheckHistory() {

    }

    override fun getBinding() = HistoryDetailFragmentBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(): HistoryDetailFragment {
            return HistoryDetailFragment()
        }
    }
}