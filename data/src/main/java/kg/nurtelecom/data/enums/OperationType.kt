package kg.nurtelecom.data.enums

enum class OperationType(val type: String) {
    SALE("Продажа"),
    RETURN_SALE("Возврат продажи"),
    PREPAY("Предоплата"),
    BUY("Покупка"),
    RETURN_BUY("Возврат покупки"),
    PREPAY_CLOSE("Закрытие предоплаты"),
    RETURN_PREPAY("Возврат предоплаты"),
    POSTPAY("Кредит"),
    POSTPAY_CLOSE("Закрытие кредита"),
    RETURN_POSTPAY("Возврат кредита");
}