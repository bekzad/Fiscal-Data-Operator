package kg.nurtelecom.data

data class ChangePasswordBody(val currentPassword:String, val newPassword:String, val confirmPassword:String)
