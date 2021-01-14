package kg.nurtelecom.sell.ui.fragment.history.detail

import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.sell.databinding.ChecksHistoryDetailFragmentBinding
import kg.nurtelecom.sell.ui.fragment.history.HistoryViewModel

class HistoryDetailFragment : CoreFragment<ChecksHistoryDetailFragmentBinding, HistoryViewModel>(HistoryViewModel::class) {

    override fun setupViews() {
        val someInt = requireArguments().getInt("check_id")
        vm.fetchDetailCheckHistory(someInt)
    }

    override fun getBinding() = ChecksHistoryDetailFragmentBinding.inflate(layoutInflater)

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        vm.detailCheckHistory.observe(viewLifecycleOwner) {
            parentActivity.setToolbarTitle("Кассовый чек №${it.receipt.id}")
        }
    }

    companion object {
        fun newInstance(): HistoryDetailFragment {
            return HistoryDetailFragment()
        }
    }
}