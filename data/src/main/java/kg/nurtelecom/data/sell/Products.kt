package kg.nurtelecom.data.sell

import java.math.BigDecimal


data class Products (
	val id : Long,
	val code : String,
	val name : String,
	val tnvdCode : String,
	val markTypeId : Long,
	val markCode : String,
	val price : BigDecimal,
	val unit : String
)