package kg.nurtelecom.data.history

import java.math.BigDecimal

data class Content (
	val total : BigDecimal,
	val gnsRegNum : Int,
	val indexNum : Int,
	val subtotal : Double,
	val operationType : String,
	val totalGoodsSum : Double,
	val createdAt : String,
	val tspName : String,
	val id : Long
)