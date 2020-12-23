package kg.nurtelecom.data.sell

import java.math.BigDecimal

data class Product(
    val price: BigDecimal? = null,
    val count: BigDecimal,
    val totalPrice: BigDecimal,
    val discount: BigDecimal? = null,
    val allowance: BigDecimal? = null
)
