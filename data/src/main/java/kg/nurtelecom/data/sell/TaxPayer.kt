package kg.nurtelecom.data.sell


data class TaxPayer (
	val id : Long,
	val type : String,
	val inn : String,
	val personalNum : Long,
	val name : String,
	val msisdn : String,
	val email : String,
	val legalAddress : String,
	val personName : String,
	val ofdStatus : String
)