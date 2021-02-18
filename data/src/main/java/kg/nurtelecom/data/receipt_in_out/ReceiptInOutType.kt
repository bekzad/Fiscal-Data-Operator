package kg.nurtelecom.data.receipt_in_out

enum class ReceiptInOutType(val type: String) {
    INCOME("Чек внесения в кассу"),
    OUTCOME("Чек выплаты из кассы")
}
