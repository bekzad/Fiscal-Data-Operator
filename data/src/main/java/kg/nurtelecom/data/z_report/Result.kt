package kg.nurtelecom.data.z_report

data class Result (
	val taxSalesPointName : String,
	val inn : String,
	val cashRegisterId : Int,
	val gnsRegNum : Int,
	val cashRegisterName : String,
	val cashRegisterVersion : String,
	val taxPayerName : String,
	val taxAccountingMethodName : String,
	val report : Report,
	val operationDetails : List<String>,
	val paymentDetails : List<String>,
	val details : List<String>
)