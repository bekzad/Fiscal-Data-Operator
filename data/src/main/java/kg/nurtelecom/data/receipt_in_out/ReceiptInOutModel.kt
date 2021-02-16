package kg.nurtelecom.data.receipt_in_out

import java.math.BigDecimal

data class ReceiptInOutModel(
        val id: Long,
        val receiptType: ReceiptInOutType,
        val sum: BigDecimal,
        val indexNum: Long,
        val createdAt: String
)
