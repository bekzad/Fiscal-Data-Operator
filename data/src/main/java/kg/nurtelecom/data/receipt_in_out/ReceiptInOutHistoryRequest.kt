package kg.nurtelecom.data.receipt_in_out

data class ReceiptInOutHistoryRequest(
    val fromDate: String,
    val toDate: String,
    val receiptType: ReceiptInOutType?,
    val indexNum: Long?
)