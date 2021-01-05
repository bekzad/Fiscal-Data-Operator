package kg.nurtelecom.core.extension

enum class OperationType(val type: String) {
    SALE("Продажа"),
    RETURN_SALE("Возврат продажи"),
    PREPAY("Предоплата")
}