package kg.nurtelecom.data.receipt_in_out

data class ReceiptInOutRequestResult(
        val result: ReceiptInOutResult,
        val resultCode: String,
        val detail: String,
        val resultDetail: String
)