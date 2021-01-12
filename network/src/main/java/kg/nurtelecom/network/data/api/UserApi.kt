package kg.nurtelecom.network.data.api

import kg.nurtelecom.data.UserUpdateResult
import retrofit2.http.*


interface UserApi {

    @Headers("Authorization: Basic ZGV2OkZndkRlNHZkITM={token}", "session_uuid: d34a4f45-a424-418a-8efe-edc0c108cdf1")
    @POST("update-profile")
    suspend fun sendingData(
        @Header("token") token: String,
        @Query("mssisdn") mssiSdn: String,
        @Query("firstname") firstName: String,
        @Query("lastname") lastName: String,
        @Query("middlename") middleName: String,
        @Query("inn") inn: String,
        @Query("id") id: Long? = null)

    : UserUpdateResult
}
