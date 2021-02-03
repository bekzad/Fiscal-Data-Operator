package kg.nurtelecom.sell.ui.fragment.credit

import androidx.lifecycle.MutableLiveData
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.data.history_by_id.Result
import kg.nurtelecom.sell.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers

abstract class CreditListVM : CoreViewModel() {

    abstract var creditListData: MutableLiveData<List<Content>>
    abstract var detailCreditList: MutableLiveData<Result>
    abstract fun fetchChecksHistory()
    abstract fun fetchDetailCheckHistory(id: Int)
}

class CreditListVMImpl (private val creditListRepository: HistoryRepository) : CreditListVM(){
    override var creditListData: MutableLiveData<List<Content>> = MutableLiveData()
    override var detailCreditList: MutableLiveData<Result> = MutableLiveData()

    override fun fetchChecksHistory() {
        safeCall(Dispatchers.IO) {
            creditListData.postValue(creditListRepository.fetchChecksHistory())
        }
    }
    override fun fetchDetailCheckHistory(id: Int) {
        safeCall (Dispatchers.IO){
            detailCreditList.postValue(creditListRepository.fetchDetailCheckHistory(id))
        }
    }
}