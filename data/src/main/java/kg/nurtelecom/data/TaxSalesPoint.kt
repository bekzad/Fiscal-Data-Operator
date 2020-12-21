package kg.nurtelecom.data

data class TaxSalesPoint (
    val id : Int,
    val name : String,
    val factAddress : String,
    val gpsX : Int,
    val gpsY : Int,
    val gnsSalesPointId : Int,
    val taxPayerId : Int,
    val ndsType : String,
    val ofdStatus : String,
    val taxAccountingMethod : String
)
