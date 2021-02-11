package kg.nurtelecom.sell.core

interface ItemClickListener {
    fun <T> onItemClick(value: T)
}