package kg.nurtelecom.data.receipt.result

import kg.nurtelecom.data.enums.OperationType
import kg.nurtelecom.data.enums.PaymentType
import java.math.BigDecimal
import java.time.LocalDateTime

data class Receipt (
    val id: Long, // id чека
    val receiptUuid: String, // Update
    val operationType: OperationType,
    val paymentType: PaymentType,
    val discountTotal: BigDecimal, // сумма всех скидок по позициям: sum(discount)
    val chargeTotal: BigDecimal, // сумма всех надбавок по позициям: sum(charge)
    val oddAmount: BigDecimal, // сдача - пока 0
    val receivedAmount: BigDecimal, // Update
    val nspAmountTotal: BigDecimal, // sum(productNspAmount + nspCharge - nspDiscount)
    val ndsAmountTotal: BigDecimal, // sum(productNdsAmount + ndsCharge - ndsDiscount)
    val subtotal: BigDecimal, // сумма без учета всего(налог скидки): sum(subtotal)
    val totalGoodsSum: BigDecimal, // Update
    val total: BigDecimal, // окончательно с учетом (налог скидки): subtotal + nspAmountTotal + ndsAmountTotal - discountTotal + sum(charge)
    val qrCode: String,
    val checkCode: String, // Update
    val indexNum: Long, // счетчик чеков: последний для этого ККМ + 1
    val rollBackReceiptNum: String, // Update
    val createdAt: String, // Should be LocalDateTime
    val shiftId: Long, // id смены
    val receiptItems: List<ReceiptItemResult>, // позиции чека
)