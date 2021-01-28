package kg.nurtelecom.data.z_report

data class Report (
	val id : Int,
	val indexNum : Int,
	val fiscalMode : Boolean,
	val shiftSoldAmount : Int,
	val shiftReturnAmount : Int,
	val shiftDiscountAmount : Int,
	val shiftChargeAmount : Int,
	val shiftAmountIn : Int,
	val shiftAmountOut : Int,
	val shiftTotalAmount : Int,
	val shiftReceiptCount : Int,
	val shiftId : Int,
	val fiscalTimeTotalAmount : Double,
	val gnsStatus : String,
	val gnsSynchronizedDate : String,
	val checkCode : Int,
	val createDate : String
)