package kg.nurtelecom.data.receipt.request

import kg.nurtelecom.data.enums.OperationType
import kg.nurtelecom.data.enums.PaymentType

data class FetchReceiptRequest (
    val operationType: OperationType, // тип операции
    val paymentType: PaymentType, //тип платежа
    val receiptItems: List<ReceiptItemRequest>, // позиции чека

    // Needs to be checked, no id in example request, it will send null by default
    val id: Long? = null // id чека для отмены, не обязательное
)
