package kg.nurtelecom.sell.ui.fragment.history

import androidx.lifecycle.MutableLiveData
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.sell.repository.SellRepository

abstract class HistoryViewModel : CoreViewModel() {
    abstract var checksHistoryData: MutableLiveData<List<Content>>
    abstract fun fetchChecksHistory()
}

class HistoryViewModelImpl (private val sellRepository: SellRepository) : HistoryViewModel() {
    override var  checksHistoryData: MutableLiveData<List<Content>> = MutableLiveData()

    override fun fetchChecksHistory() {
        safeUICall {
            checksHistoryData.postValue(sellRepository.fetchChecksHistory())
        }
    }
}