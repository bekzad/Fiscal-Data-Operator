package kg.nurtelecom.sell.ui.fragment.history.search

import androidx.recyclerview.widget.LinearLayoutManager
import kg.nurtelecom.core.extension.formatForDecoratorDateTimeDefaults
import kg.nurtelecom.sell.databinding.ChecksHistoryDetailFragmentBinding
import kg.nurtelecom.sell.databinding.ChecksHistorySearchFragmentBinding
import kg.nurtelecom.sell.ui.core.CoreFragment
import kg.nurtelecom.sell.ui.fragment.history.HistoryViewModel
import java.text.SimpleDateFormat


class HistorySearchFragment : kg.nurtelecom.core.fragment.CoreFragment<ChecksHistorySearchFragmentBinding, HistoryViewModel>(HistoryViewModel::class) {

    companion object {
        fun newInstance(): HistorySearchFragment {
            return HistorySearchFragment()
        }
    }

    override fun getBinding(): ChecksHistorySearchFragmentBinding {
        TODO("Not yet implemented")
    }
}