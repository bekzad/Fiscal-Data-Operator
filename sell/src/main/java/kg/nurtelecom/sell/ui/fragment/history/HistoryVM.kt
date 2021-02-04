package kg.nurtelecom.sell.ui.fragment.history

import androidx.lifecycle.MutableLiveData
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.data.history_by_id.Result
import kg.nurtelecom.sell.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers

abstract class HistoryViewModel : CoreViewModel() {
    abstract var checksHistoryData: MutableLiveData<List<Content>>
    abstract var detailCheckHistory: MutableLiveData<Result>
    abstract fun fetchChecksHistory()
    abstract fun fetchDetailCheckHistory(id: Int)
}

class HistoryViewModelImpl (private val historyRepository: HistoryRepository) : HistoryViewModel() {
    override var  checksHistoryData: MutableLiveData<List<Content>> = MutableLiveData()
    override var  detailCheckHistory: MutableLiveData<Result> = MutableLiveData()

    override fun fetchChecksHistory() {
        safeCall(Dispatchers.IO) {
            checksHistoryData.postValue(historyRepository.fetchChecksHistory())
        }
    }

    override fun fetchDetailCheckHistory(id: Int) {
        safeCall(Dispatchers.IO) {
            detailCheckHistory.postValue(historyRepository.fetchDetailCheckHistory(id))
        }
    }
}
