package kg.nurtelecom.data.history_by_id

data class ReceiptItems (
	val id : Int,
	val productId : String,
	val productName : String,
	val productCode : String,
	val productQuantity : Int,
	val productUnitPrice : Int,
	val productUnitMeasure : String,
	val productNdsType : Int,
	val productNdsAmount : Double,
	val productNspType : Double,
	val productNspAmount : Double,
	val productMarkType : String,
	val productMarkCode : String,
	val discount : Double,
	val charge : Double,
	val subtotal : Double,
	val total : Double,
	val itemIndex : Double
)