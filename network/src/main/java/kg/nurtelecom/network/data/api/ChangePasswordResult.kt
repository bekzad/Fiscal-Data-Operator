package kg.nurtelecom.network.data.api

data class ChangePasswordResult(
    val result: Boolean,
    val resultCode: String,
    val details: String? = null,
    val resultDetail: String? = null
)
