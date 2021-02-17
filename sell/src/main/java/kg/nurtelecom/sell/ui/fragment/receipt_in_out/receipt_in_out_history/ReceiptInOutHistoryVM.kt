package kg.nurtelecom.sell.ui.fragment.receipt_in_out.receipt_in_out_history

import androidx.lifecycle.MutableLiveData
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutHistoryModel
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutHistoryRequest
import kg.nurtelecom.sell.repository.ReceiptInOutRepository
import kotlinx.coroutines.Dispatchers

abstract class ReceiptInOutHistoryVM : CoreViewModel() {

    abstract fun fetchReceiptInOutHistoryList()

    abstract val receiptInOutHistoryList: MutableLiveData<List<ReceiptInOutHistoryModel>>
}

class ReceiptInOutHistoryVMImpl(private val repository: ReceiptInOutRepository) : ReceiptInOutHistoryVM() {

    override val receiptInOutHistoryList: MutableLiveData<List<ReceiptInOutHistoryModel>> = MutableLiveData()


    override fun fetchReceiptInOutHistoryList() {
        safeCall(Dispatchers.Default) {
            val result = repository.fetchReceiptInOutHistoryList(ReceiptInOutHistoryRequest("10.02.2021 00:00:00", "17.02.2021 23:59:59", null, null))
            receiptInOutHistoryList.postValue(result.result.content)
        }
    }


}