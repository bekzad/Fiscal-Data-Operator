package kg.nurtelecom.data.receipt_in_out

data class ReceiptInOutHistoryRequestResult(
    val result: ReceiptInOutHistoryResult,
    val resultCode: String,
    val details: String,
    val resultDetail: String
)
