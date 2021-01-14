package kg.nurtelecom.sell.ui.fragment.history

import androidx.lifecycle.MutableLiveData
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.sell.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers

abstract class HistoryViewModel : CoreViewModel() {
    abstract var checksHistoryData: MutableLiveData<List<Content>>
    abstract fun fetchChecksHistory()
}

class HistoryViewModelImpl (private val historyRepository: HistoryRepository) : HistoryViewModel() {
    override var  checksHistoryData: MutableLiveData<List<Content>> = MutableLiveData()

    override fun fetchChecksHistory() {
        safeCall(Dispatchers.IO) {
            checksHistoryData.postValue(historyRepository.fetchChecksHistory())
        }
    }
}