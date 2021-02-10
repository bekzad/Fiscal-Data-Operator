package kg.nurtelecom.sell.core

import java.math.BigDecimal

interface ProductClickListener {
    fun selectProduct(total: BigDecimal)
}