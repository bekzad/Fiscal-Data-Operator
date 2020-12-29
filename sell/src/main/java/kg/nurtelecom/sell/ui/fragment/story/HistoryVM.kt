package kg.nurtelecom.sell.ui.fragment.story

import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.sell.repository.SellRepository

abstract class HistoryViewModel : CoreViewModel() {
    abstract fun fetchCheckHistory()
}

class HistoryViewModelImpl (private val sellRepository: SellRepository) : HistoryViewModel() {
    override fun fetchCheckHistory() {
        safeUICall {
            sellRepository.fetchCheckHistory()
        }
    }
}
