package kg.nurtelecom.data.receipt_in_out

import java.math.BigDecimal

data class ReceiptInOutRequest(
        val id: Long?,
        val receiptType: ReceiptInOutType,
        val sum: BigDecimal
)
