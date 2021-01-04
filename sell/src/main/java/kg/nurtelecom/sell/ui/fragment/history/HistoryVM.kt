package kg.nurtelecom.sell.ui.fragment.history

import androidx.lifecycle.MutableLiveData
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.sell.repository.SellRepository

abstract class HistoryViewModel : CoreViewModel() {
    abstract var checkHistoryData: MutableLiveData<List<Content>>
    abstract fun fetchCheckHistory()
}

class HistoryViewModelImpl (private val sellRepository: SellRepository) : HistoryViewModel() {
    override var  checkHistoryData: MutableLiveData<List<Content>> = MutableLiveData()

    override fun fetchCheckHistory() {
        safeUICall {
            sellRepository.insertCheckHistory()
            checkHistoryData.postValue(sellRepository.fetchCheckHistory())
        }
    }
}