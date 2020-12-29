package kg.nurtelecom.sell.ui.fragment.story

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kg.nurtelecom.core.extension.setToolbarTitle
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.HistoryFragmentBinding

class HistoryFragment : CoreFragment<HistoryFragmentBinding, HistoryViewModel>(HistoryViewModel::class) {

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).setToolbarTitle(R.string.history_title)
    }

    override fun setupViews() {
        vm.fetchCheckHistory()
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        observeCheckHistory()
    }

    private fun observeCheckHistory() {
        vm.event.observe(this, Observer {
            Log.d("IT", it.toString())
        })
    }

    override fun getBinding() = HistoryFragmentBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }
}
