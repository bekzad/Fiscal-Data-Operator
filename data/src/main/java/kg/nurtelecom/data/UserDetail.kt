package kg.nurtelecom.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserDetail (
    @PrimaryKey val id : Int,
    val msisdn : String,
    val firstname : String,
    val lastname : String,
    val middlename : String,
    val inn : String
)
