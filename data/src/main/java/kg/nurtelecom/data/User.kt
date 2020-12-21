package kg.nurtelecom.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
	@PrimaryKey val id : Int,
	val role : Role,
	val username : String,
	val enabled : Boolean,
	val initialPassword : String,
	val changePassword : Boolean,
	val userDetail : UserDetail,
	val failLogin : String
)
