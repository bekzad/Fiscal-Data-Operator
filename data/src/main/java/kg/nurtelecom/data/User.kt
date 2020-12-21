package kg.nurtelecom.data

data class User (
    val role : Role,
    val username : String,
    val enabled : Boolean,
    val initialPassword : String,
    val changePassword : Boolean,
    val userDetail : UserDetail,
    val failLogin : String
)

