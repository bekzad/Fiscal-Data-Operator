package kg.nurtelecom.sell.ui.fragment.receipt_in_out

import android.util.Log
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutRequest
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutType
import kg.nurtelecom.sell.repository.ReceiptInOutRepository
import kotlinx.coroutines.Dispatchers
import java.math.BigDecimal


abstract class ReceiptInOutVM : CoreViewModel() {
    abstract fun generateReceiptInOut()

    abstract var receiptInOutType: ReceiptInOutType
    abstract var sum: BigDecimal
}

class ReceiptInOutVMImpl(private val receiptInOutRepository: ReceiptInOutRepository) : ReceiptInOutVM() {

    override var receiptInOutType: ReceiptInOutType = ReceiptInOutType.INCOME
    override var sum: BigDecimal = BigDecimal.ZERO


    override fun generateReceiptInOut() {

        val request = ReceiptInOutRequest(null, receiptInOutType, sum)
        Log.i("ERLAN", request.toString())

        safeCall(Dispatchers.Default) {
            Log.i("ERLAN", receiptInOutRepository.generateReceiptInOut().toString())
        }
    }



}