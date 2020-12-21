package kg.nurtelecom.network.data.api

import kg.nurtelecom.data.UserDetailModel
import retrofit2.http.Field
import retrofit2.http.POST


interface UserApi {

    @POST("update-profile")
    fun sendingData( // отправка данных
        @Field("id") id: Long,
        @Field("mssisdn") mssiSdn: String,
        @Field("firstname") firstName: String,
        @Field("lastname") lastName: String,
        @Field("middlename") middleName: String,
        @Field("inn") inn: String)
    : UserDetailModel

}
