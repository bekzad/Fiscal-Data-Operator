package kg.nurtelecom.data.receipt.result

import java.io.Serializable

data class FetchReceiptResult (
    val result: Result, // сгенерированный чек для печати
    val resultCode: String, // Успешность запроса (SUCCESS, FAIL, EXCEPTION)

    // Needs to be checked if they send null
    val details: String, // Описание ошибки в случае не успешного запроса
    val resultDetail: String // Код ошибки в случае неуспешности запроса
) : Serializable