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
	val productNdsAmount : Int,
	val productNspType : Int,
	val productNspAmount : Int,
	val productMarkType : String,
	val productMarkCode : String,
	val discount : Int,
	val charge : Int,
	val subtotal : Int,
	val total : Int,
	val itemIndex : Int
)