package kg.nurtelecom.data.receipt.result

import kg.nurtelecom.data.enums.NdsType
import kg.nurtelecom.data.enums.NspType
import java.io.Serializable
import java.math.BigDecimal

data class Result(
    val taxSalesPointName: String, // наименование ОП
    val inn: String, // ИНН ОП
    val cashRegisterId: Long, // id ККМ
    val gnsRegNum: String, // Регистрационный номер ККМ
    /* Update */
    val cashRegisterName: String,
    val cashRegisterVersion: String,
    val taxPayerName: String,
    val taxAccountingMethodName: String,
    val ndsType: NdsType,
    val nspType: NspType,
    val alreadyPayed: BigDecimal,
    val debt: String,
    val receipt: Receipt // Total receipt
    /* Update */
) : Serializable