package kg.nurtelecom.data.history_by_id

data class Result (
	val taxSalesPointName : String,
	val inn : String,
	val cashRegisterId : Int,
	val gnsRegNum : Int,
	val cashRegisterName : String,
	val cashRegisterVersion : String,
	val taxPayerName : String,
	val taxAccountingMethodName : String,
	val ndsType : String,
	val nspType : String,
	val alreadyPayed : Int,
	val receipt : Receipt,
	val debt : Double
)