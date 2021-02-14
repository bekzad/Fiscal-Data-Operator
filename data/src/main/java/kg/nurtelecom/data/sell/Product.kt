package kg.nurtelecom.data.sell

import java.math.BigDecimal

data class Product(
    var productId: Long?,
    val productName: String,
    val productQuantity: BigDecimal,
    val productUnitPrice: BigDecimal,
    val discount: BigDecimal,
    val charge: BigDecimal,
    val itemIndex: Long,
)