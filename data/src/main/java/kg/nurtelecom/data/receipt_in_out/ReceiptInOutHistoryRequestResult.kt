package kg.nurtelecom.data.receipt_in_out

data class ReceiptInOutHistoryRequestResult(
    val result: ReceiptInOutHistoryModel,
    val resultCode: String,
    val details: String,
    val resultDetail: String
)
