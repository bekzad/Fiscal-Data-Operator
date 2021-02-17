package kg.nurtelecom.sell.ui.fragment.history

import androidx.lifecycle.MutableLiveData
import kg.nurtelecom.core.extension.roundOff
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.enums.OperationType
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.data.history_by_id.ReceiptItems
import kg.nurtelecom.data.history_by_id.Result
import kg.nurtelecom.sell.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers
import java.math.BigDecimal

abstract class HistoryViewModel : CoreViewModel() {
    abstract var checksHistoryData: MutableLiveData<List<Content>>
    abstract var detailCheckHistory: MutableLiveData<Result>
    abstract var totalSum: MutableLiveData<BigDecimal>
    abstract var totalProducts: MutableLiveData<List<ReceiptItems>>
    open val filteredChecksHistory: MutableLiveData<List<Content>>? = MutableLiveData()
    abstract fun fetchChecksHistory(operationType: String? = null)
    abstract fun fetchDetailCheckHistory(id: Int)
    abstract fun searchChecks(name: String)
    abstract fun calculateTotalSum(items: ReceiptItems, isChecked: Boolean)
    abstract fun resetTotalSum()
}

class HistoryViewModelImpl (private val historyRepository: HistoryRepository) : HistoryViewModel() {
    override var  checksHistoryData: MutableLiveData<List<Content>> = MutableLiveData()
    override var  detailCheckHistory: MutableLiveData<Result> = MutableLiveData()
    override var totalSum: MutableLiveData<BigDecimal> = MutableLiveData()
    override var totalProducts: MutableLiveData<List<ReceiptItems>> = MutableLiveData()

    override fun fetchChecksHistory(operationType: String?) {
        safeCall(Dispatchers.IO) {
            checksHistoryData.postValue(historyRepository.fetchChecksHistory(operationType))
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
                        || item.indexNum.toString().contains(name, true)
            }
            filteredChecksHistory?.value = filteredList
        }
    }

    override fun calculateTotalSum(items: ReceiptItems, isChecked: Boolean) {
        totalProducts.value = listOf(items)
        if (totalSum.value == null) {totalSum.value = BigDecimal.ZERO}
        totalSum.value = if (isChecked){
            totalSum.value?.plus(items.total.roundOff(2))
        } else {
            totalSum.value?.minus(items.total.roundOff(2))
        }
    }

    override fun resetTotalSum() {
        totalSum.value = BigDecimal.ZERO
    }
}