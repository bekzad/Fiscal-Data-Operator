package kg.nurtelecom.data

import androidx.room.Entity

@Entity(tableName = "userdata")
data class UserDetailModel (

     val id: Long,
     val msiSdn: String,
     val firstName: String,
     val lastName: String,
     val middleName: String,
     val inn: String
 )

