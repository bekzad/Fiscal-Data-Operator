package kg.nurtelecom.sell.core

import kg.nurtelecom.data.sell.Products

interface ItemClickListener {
    fun transferData(product: Products)
}