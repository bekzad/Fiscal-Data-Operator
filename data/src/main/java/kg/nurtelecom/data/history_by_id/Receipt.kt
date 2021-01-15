package kg.nurtelecom.data.history_by_id

data class Receipt (
	val id : Int,
	val receiptUuid : String,
	val operationType : String,
	val paymentType : String,
	val discountTotal : Double,
	val chargeTotal : Double,
	val oddAmount : Double,
	val receivedAmount : Double,
	val nspAmountTotal : Double,
	val ndsAmountTotal : Double,
	val subtotal : Double,
	val totalGoodsSum : Double,
	val total : Double,
	val qrCode : String,
	val checkCode : Double,
	val indexNum : Int,
	val rollbackRcptNum : String,
	val createdAt : String,
	val shiftId : Int,
	val receiptItems : List<ReceiptItems>
)