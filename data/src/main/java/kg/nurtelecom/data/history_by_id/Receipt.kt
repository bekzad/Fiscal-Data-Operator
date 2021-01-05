package kg.nurtelecom.data.history_by_id

data class Receipt (
	val id : Int,
	val receiptUuid : String,
	val operationType : String,
	val paymentType : String,
	val discountTotal : Int,
	val chargeTotal : Int,
	val oddAmount : Int,
	val receivedAmount : Int,
	val nspAmountTotal : Int,
	val ndsAmountTotal : Int,
	val subtotal : Int,
	val totalGoodsSum : Int,
	val total : Int,
	val qrCode : String,
	val checkCode : Int,
	val indexNum : Int,
	val rollbackRcptNum : String,
	val createdAt : String,
	val shiftId : Int,
	val receiptItems : List<ReceiptItems>
)