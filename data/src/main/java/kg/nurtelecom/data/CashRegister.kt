package kg.nurtelecom.data

data class CashRegister (
    val id : Int,
    val gnsRegNum : Int,
    val gnsRegDate : String,
    val taxSalesPoint : TaxSalesPoint,
    val ofdStatus : String
)
