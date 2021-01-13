package kg.nurtelecom.data.sell

import java.math.BigDecimal

data class Product(
    val price: BigDecimal,
    val count: BigDecimal,
    val discount: BigDecimal,
    val allowance: BigDecimal
) {
    val totalPrice: BigDecimal
        get() {
            // We are subtracting the discount from the main amount
            // and adding an allowance to the main amount to get the total price
            val totalPrice = price.multiply(count)
            val hundred = BigDecimal("100.0")
            val discount = totalPrice.multiply(this.discount).divide(hundred)
            val allowance = totalPrice.multiply(this.allowance).divide(hundred)
            return totalPrice.subtract(discount).add(allowance)
        }
}