package kg.nurtelecom.data.receipt.request

import java.math.BigDecimal

data class ReceiptItemRequest (
    val productId: Long?, // id продукта
    val productName: String,
    val productQuantity: BigDecimal, // количество товара
    val productUnitPrice: BigDecimal, // if not set take from product
    val itemIndex: Long //счетчик позиций в пределах каждого чека, начинаем с 1
)
