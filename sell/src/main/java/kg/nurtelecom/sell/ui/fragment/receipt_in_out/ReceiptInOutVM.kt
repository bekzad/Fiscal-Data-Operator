package kg.nurtelecom.sell.ui.fragment.receipt_in_out

import kg.nurtelecom.core.CoreEvent
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutRequest
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutResult
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutType
import kg.nurtelecom.sell.repository.ReceiptInOutRepository
import kotlinx.coroutines.Dispatchers
import java.math.BigDecimal


abstract class ReceiptInOutVM : CoreViewModel() {
    abstract fun generateReceiptInOut()

    abstract var receiptInOutType: ReceiptInOutType
    abstract var sum: BigDecimal
    abstract var selectedReceiptInOut: ReceiptInOutResult?
}

class ReceiptInOutVMImpl(private val receiptInOutRepository: ReceiptInOutRepository) : ReceiptInOutVM() {

    override var receiptInOutType: ReceiptInOutType = ReceiptInOutType.INCOME
    override var sum: BigDecimal = BigDecimal.ZERO
    override var selectedReceiptInOut: ReceiptInOutResult? = null


    override fun generateReceiptInOut() {
        safeCall(Dispatchers.Default) {
            val requestBody = ReceiptInOutRequest(null, receiptInOutType, sum)
            selectedReceiptInOut = receiptInOutRepository.generateReceiptInOut(requestBody).result
            event.postValue(ReceiptInOutCreated())
        }
    }
}

class ReceiptInOutCreated() : CoreEvent()