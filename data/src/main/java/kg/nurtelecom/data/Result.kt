package kg.nurtelecom.data

data class Result (
    val user : User,
    val ofdUserType : String,
    val cashRegister : CashRegister,
    val enabled : Boolean,
    val username : String,
    val authorities : List<Authorities>,
    val accountNonExpired : Boolean,
    val accountNonLocked : Boolean,
    val credentialsNonExpired : Boolean
)
