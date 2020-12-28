package kg.nurtelecom.sell.ui.fragment.adapter

data class Product(
    val price: Double,
    val count: Double,
    val totalPrice: Double,
    val discount: Double? = null,
    val allowance: Double? = null
)
