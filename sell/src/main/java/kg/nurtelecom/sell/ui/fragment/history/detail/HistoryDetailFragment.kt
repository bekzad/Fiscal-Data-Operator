package kg.nurtelecom.sell.ui.fragment.history.detail

import androidx.appcompat.app.AppCompatActivity
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.ChecksHistoryDetailFragmentBinding
import kg.nurtelecom.sell.ui.fragment.history.HistoryAdapter
import kg.nurtelecom.sell.ui.fragment.history.HistoryViewModel

class HistoryDetailFragment : CoreFragment<ChecksHistoryDetailFragmentBinding, HistoryViewModel>(HistoryViewModel::class) {

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

    override fun getBinding() = ChecksHistoryDetailFragmentBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(): HistoryDetailFragment {
            return HistoryDetailFragment()
        }
    }
}