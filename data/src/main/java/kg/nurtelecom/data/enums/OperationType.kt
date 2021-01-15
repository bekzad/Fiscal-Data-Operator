package kg.nurtelecom.data.enums

enum class OperationType(val type: String) {
    SALE("Продажа"),
    RETURN_SALE("Возврат продажи"),
    PREPAY("Предоплата"),
    BUY("Покупка"),
    RETURN_BUY("Возврат покупки"),
    PREPAY_CLOSE("Закрытие предоплаты (аванса)"),
    RETURN_PREPAY("Возврат предоплаты (аванса)"),
    POSTPAY("Пост оплата (кредит)"),
    POSTPAY_CLOSE("Закрытие пост оплаты (кредита)"),
    RETURN_POSTPAY("Возврат пост оплаты (кредита)");
}