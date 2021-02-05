package kg.nurtelecom.data.receipt.result

import java.math.BigDecimal

data class Result(
    val taxSalesPointName: String, // наименование ОП
    val inn: String, // ИНН ОП
    val cashRegisterId: Long, // id ККМ
    val gnsRegNum: String, // Регистрационный номер ККМ
    // Update after this
    val cashRegisterName: String,
    val cashRegisterVersion: String,
    val taxPayerName: String,
    val taxAccountingMethodName: String,
    val ndsType: String,
    val nspType: String,
    val alreadyPayed: BigDecimal,
    val debt: String,
    val receipt: Receipt // Total receipt
)