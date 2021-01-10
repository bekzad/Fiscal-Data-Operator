package kg.nurtelecom.data.sell

import java.math.BigDecimal

data class Product(
    val price: BigDecimal,
    val count: BigDecimal,
    val totalPrice: BigDecimal,
    private val discount: BigDecimal,
    private val allowance: BigDecimal
) {
    val verifiedDiscount = if (discount.isNotZero()) ("+ $discount% ") else ""
    val verifiedAllowance = if (allowance.isNotZero()) ("+ $allowance%") else ""
}

fun BigDecimal.isNotZero(): Boolean {
    return compareTo(BigDecimal.ZERO) != 0
}

fun Product.fetchExpression(): StringBuilder {
    return StringBuilder().also {
        it.append("$price * ")
        it.append("${count.toInt()} ")
        it.append(verifiedDiscount)
        it.append(verifiedAllowance)
        it.trimEnd()
    }
}