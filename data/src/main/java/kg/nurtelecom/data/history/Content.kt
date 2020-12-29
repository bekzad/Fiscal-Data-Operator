package kg.nurtelecom.data.history

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class Content (
	val total : Double,
	val gnsRegNum : Int,
	val indexNum : Int,
	val subtotal : Int,
	val operationType : String,
	val totalGoodsSum : Double,
	val createdAt : String,
	val tspName : String,
	@PrimaryKey val id : Int
)