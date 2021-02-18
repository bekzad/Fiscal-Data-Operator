package kg.nurtelecom.data.receipt_in_out

import java.io.Serializable

data class ReceiptInOutResult(
        val taxSalesPointName: String,
        val inn: String,
        val cashRegisterId: Long,
        val gnsRegNum: String,
        val cashRegisterName: String,
        val cashRegisterVersion: String,
        val taxPayerName: String,
        val taxAccountingMethodName: String,
        val receiptInOutModel: ReceiptInOutModel
): Serializable