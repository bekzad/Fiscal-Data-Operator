package kg.nurtelecom.sell.ui.fragment.receipt_in_out.receipt_in_out_history

import androidx.lifecycle.MutableLiveData
import kg.nurtelecom.core.CoreEvent
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutHistoryModel
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutResult
import kg.nurtelecom.sell.repository.ReceiptInOutRepository
import kotlinx.coroutines.Dispatchers

abstract class ReceiptInOutHistoryVM : CoreViewModel() {

    abstract fun fetchReceiptInOutHistoryList()
    abstract fun fetchReceiptInOutById(id: Long)

    abstract val receiptInOutHistoryList: MutableLiveData<List<ReceiptInOutHistoryModel>>
}

class ReceiptInOutHistoryVMImpl(private val repository: ReceiptInOutRepository) : ReceiptInOutHistoryVM() {

    override val receiptInOutHistoryList: MutableLiveData<List<ReceiptInOutHistoryModel>> = MutableLiveData()

    override fun fetchReceiptInOutHistoryList() {
        safeCall(Dispatchers.Default) {
            val result = repository.fetchReceiptInOutHistoryList()
            receiptInOutHistoryList.postValue(result.result.content)
        }
    }

    override fun fetchReceiptInOutById(id: Long) {
        safeCall(Dispatchers.Default) {
            val result = repository.fetchReceiptInOutById(id)
            event.postValue(ReceiptInOutFetched(result))
        }
    }
}

class ReceiptInOutFetched(val receipt: ReceiptInOutResult) : CoreEvent()