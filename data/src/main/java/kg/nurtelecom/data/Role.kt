package kg.nurtelecom.data

data class Role (
    val id : Int,
    val name : String,
    val description : String,
    val canAccessAdminApp : Boolean
)

