package kg.nurtelecom.sell.ui.fragment.history

import androidx.lifecycle.MutableLiveData
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.enums.OperationType
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.data.history_by_id.Result
import kg.nurtelecom.sell.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers

abstract class HistoryViewModel : CoreViewModel() {
    abstract var checksHistoryData: MutableLiveData<List<Content>>
    abstract var detailCheckHistory: MutableLiveData<Result>
    open val filteredChecksHistory: MutableLiveData<List<Content>>? = MutableLiveData()
    abstract fun fetchChecksHistory()
    abstract fun fetchDetailCheckHistory(id: Int)
    abstract fun searchChecks(name: String)
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

    override fun searchChecks(name: String) {
        val searchList = mutableListOf<Content>()
        checksHistoryData.value?.let { list ->
            list.forEach { item ->
                searchList.add(item)
            }
            val filteredList = searchList.filter { item ->
                OperationType.valueOf(item.operationType).type.contains(name, true)
            }
            filteredChecksHistory?.value = filteredList
        }
    }
}