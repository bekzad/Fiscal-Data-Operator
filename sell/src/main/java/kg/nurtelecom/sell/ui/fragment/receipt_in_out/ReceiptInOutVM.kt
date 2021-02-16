package kg.nurtelecom.sell.ui.fragment.receipt_in_out

import android.util.Log
import kg.nurtelecom.core.viewmodel.CoreViewModel
import kg.nurtelecom.sell.repository.ReceiptInOutRepository
import kotlinx.coroutines.Dispatchers


abstract class ReceiptInOutVM : CoreViewModel() {
    abstract fun generateReceiptInOut()
}

class ReceiptInOutVMImpl(private val receiptInOutRepository: ReceiptInOutRepository) : ReceiptInOutVM() {

    override fun generateReceiptInOut() {
        safeCall(Dispatchers.Default) {
            Log.i("ERLAN", receiptInOutRepository.generateReceiptInOut().toString())
        }
    }

}