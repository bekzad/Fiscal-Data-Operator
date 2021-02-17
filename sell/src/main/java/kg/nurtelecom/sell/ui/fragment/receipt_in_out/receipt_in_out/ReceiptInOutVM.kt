package kg.nurtelecom.sell.ui.fragment.receipt_in_out.receipt_in_out

import kg.nurtelecom.core.CoreEvent
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutRequest
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutResult
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutType
import kg.nurtelecom.sell.repository.ReceiptInOutRepository
import kotlinx.coroutines.Dispatchers
import java.math.BigDecimal


abstract class ReceiptInOutVM : CoreViewModel() {
    abstract fun generateReceiptInOut(sum: BigDecimal)

    abstract var receiptInOutType: ReceiptInOutType
}

class ReceiptInOutVMImpl(private val receiptInOutRepository: ReceiptInOutRepository) : ReceiptInOutVM() {

    override var receiptInOutType: ReceiptInOutType = ReceiptInOutType.INCOME

    override fun generateReceiptInOut(sum: BigDecimal) {
        safeCall(Dispatchers.Default) {
            val requestBody = ReceiptInOutRequest(null, receiptInOutType, sum)
            val receipt = receiptInOutRepository.generateReceiptInOut(requestBody).result
            event.postValue(ReceiptInOutCreated(receipt))
        }
    }
}

class ReceiptInOutCreated(val receipt: ReceiptInOutResult) : CoreEvent()