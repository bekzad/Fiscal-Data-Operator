package kg.nurtelecom.sell.ui.fragment.history.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.ChecksHistoryDetailFragmentBinding
import kg.nurtelecom.sell.ui.fragment.history.HistoryViewModel

class HistoryDetailFragment : CoreFragment<ChecksHistoryDetailFragmentBinding, HistoryViewModel>(HistoryViewModel::class) {

    override fun setupViews() {
        val someInt = requireArguments().getInt("some_id")
        vm.fetchDetailCheckHistory(someInt)
    }

    override fun getBinding() = ChecksHistoryDetailFragmentBinding.inflate(layoutInflater)

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        observeCheckHistory()
    }

    // TODO("change fun name to subscribe")
    private fun observeCheckHistory() {
        vm.detailCheckHistory.observe(viewLifecycleOwner) {
            Log.d("it", it.toString())
            parentActivity.setToolbarTitle("Кассовый чек №${it.receipt.id}")
        }
    }

    companion object {
        fun newInstance(): HistoryDetailFragment {
            return HistoryDetailFragment()
        }
    }
}