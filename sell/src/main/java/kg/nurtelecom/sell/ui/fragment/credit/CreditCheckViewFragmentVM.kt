package kg.nurtelecom.sell.ui.fragment.credit

import androidx.lifecycle.MutableLiveData
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.data.history_by_id.Result
import kg.nurtelecom.sell.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers

abstract class CreditCheckViewFragmentVM : CoreViewModel() {
    abstract var creditCheckData: MutableLiveData<List<Content>>
    abstract var detailCreditCheckHistory: MutableLiveData<Result>
    abstract fun fetchChecksHistory()
    abstract fun fetchDetailCheckHistory(id: Int)

}
class CreditCheckViewFragmentVMImpl(private val creditCheckViewRepository : HistoryRepository): CreditCheckViewFragmentVM (){
    override var  creditCheckData: MutableLiveData<List<Content>> = MutableLiveData()
    override var  detailCreditCheckHistory: MutableLiveData<Result> = MutableLiveData()

    override fun fetchChecksHistory() {
        safeCall(Dispatchers.IO) {
            creditCheckData.postValue(creditCheckViewRepository.fetchChecksHistory())
        }
    }
    override fun fetchDetailCheckHistory(id: Int) {
        safeCall(Dispatchers.IO) {
            detailCreditCheckHistory.postValue(creditCheckViewRepository.fetchDetailCheckHistory(id))
        }
    }
}