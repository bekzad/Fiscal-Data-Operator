package kg.nurtelecom.data.receipt.result

import java.math.BigDecimal

data class ReceiptItemResult (
    val id: Long, // id позиции чека
    val productId: Long, // id товара
    val productName: String, // наименование товара
    val productCode: String, // код товара
    val productQuantity: BigDecimal, // кол-во товара
    val productUnitPrice: BigDecimal, // стоимость товара
    val productUnitMeasure: String, // единицы измерения
    val discount: BigDecimal, // скидка
    val charge: BigDecimal, // надбавка
    val subTotal: BigDecimal, // сумма без учета всего (налоги, скидки, надбавки)
    val itemIndex: Long, // счетчик позиций в пределах каждого чека, начинаем с 1
)
