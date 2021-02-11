package kg.nurtelecom.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AccessToken(
    val access_token: String,
    val token_type: String,
    val refresh_token: String,
    @PrimaryKey val expires_in: Int,
    val scope: String
)

