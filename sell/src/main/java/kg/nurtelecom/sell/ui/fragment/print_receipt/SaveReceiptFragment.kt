package kg.nurtelecom.sell.ui.fragment.print_receipt

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import kg.nurtelecom.core.extension.enable
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.data.receipt.result.FetchReceiptResult
import kg.nurtelecom.data.receipt.result.Receipt
import kg.nurtelecom.data.receipt.result.ReceiptItemResult
import kg.nurtelecom.data.receipt.result.Result
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.FragmentSaveReceiptBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.utils.isNotZero
import kg.nurtelecom.sell.utils.roundUp
import java.math.BigDecimal
import java.text.SimpleDateFormat

class SaveReceiptFragment : CoreFragment<FragmentSaveReceiptBinding, SellMainViewModel>(SellMainViewModel::class) {

    private var amountPaidVar: BigDecimal = BigDecimal.ZERO

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSaveReceiptBinding = FragmentSaveReceiptBinding.inflate(inflater, container, false)

    override fun setupToolbar(): Int = R.string.print_check

    override fun setupViews() {
        vb.btnPrintCheck.setOnClickListener {
            navigateToPrintReceipt()
        }
    }

    override fun subscribeToLiveData() {

        vm.amountPaid.observe(viewLifecycleOwner, { amountPaid ->
            amountPaidVar = amountPaid
        })

        // TO DO exception when I am trying to observe null object only when the code is 500
        vm.fetchReceiptResult.observe(viewLifecycleOwner, { response ->
            if (response != null) {
                updateScreen(response)
                vb.svReceiptContainer.visibility = View.VISIBLE
                vb.btnPrintCheck.enable(true)
            } else {
                vb.svReceiptContainer.visibility = View.GONE
                vb.btnPrintCheck.enable(false)
            }
        })
    }

    private fun updateScreen(response: FetchReceiptResult) {
        // Get the receipt
        val receipt = response.result?.receipt
        val receiptNumber = receipt?.indexNum.toString()
        val receiptItems = receipt?.receiptItems
        val qrCodeString = receipt?.qrCode ?: ""

        // Get the receiptItems and totalPrices as one string to assign to a TextView
        val receiptItemsMap = retrieveReceiptItems(receiptItems)
        val totalPricesMap = retrieveTotalPrices(response.result)
        val amountPaidMap = retrieveAmountPaid(receipt)
        val cashierInformation = retrieveCashierInfo(response.result)
        val qrCode = generateQRCode(qrCodeString)

        // Set the values of textViews
        vb.tvTaxSalesPointName.text = response.result?.taxSalesPointName
        vb.tvReceiptNumber.text = getString(R.string.receipt_number, receiptNumber)
        vb.tvProductName.text = receiptItemsMap["names"]
        vb.tvReceiptItems.text = receiptItemsMap["items"]
        vb.tvTotalPricesText.text = totalPricesMap["texts"]
        vb.tvTotalPrices.text = totalPricesMap["prices"]
        vb.tvAmountPaidText.text = amountPaidMap["amountPaidText"]
        vb.tvAmountPaid.text = amountPaidMap["amountPaidValue"]
        vb.cashierInformation.text = cashierInformation
        vb.tvReceiptInfo.text = getString(R.string.receipt_info, receiptNumber)
        vb.ivQrCode.setImageBitmap(qrCode)
    }

    private fun retrieveReceiptItems(receiptItems: List<ReceiptItemResult>?): HashMap<String, StringBuilder> {
        val nameBuilder = StringBuilder()
        val itemBuilder = StringBuilder()

        if (!receiptItems.isNullOrEmpty()) {
            for (receipt in receiptItems) {
                var productNames: String
                val receiptItem: String
                with(receipt) {
                    val productUnitMeasureStr = productUnitMeasure ?: ""
                    if (!productCode.isNullOrEmpty()) {
                        productNames = "$productName\n$productCode [M] ($productCode)\n\n"
                    } else {
                        productNames = "$productName\n\n\n"
                    }
                    receiptItem = "${productUnitPrice}??. * ${productQuantity}${productUnitMeasureStr} = ${subtotal.roundUp()}??.\n\n\n"
                }
                nameBuilder.append(productNames)
                itemBuilder.append(receiptItem)
            }
        }
        return hashMapOf("names" to nameBuilder, "items" to itemBuilder)
    }

    private fun retrieveTotalPrices(result: Result?): HashMap<String, StringBuilder> {
        val totalPrices = StringBuilder()
        val totalPricesText = StringBuilder()
        val receipt = result?.receipt

        val subtotal = "=${receipt?.subtotal?.roundUp()}??.\n"
        val discountTotal = "=${receipt?.discountTotal?.roundUp()}??.\n"
        val chargeTotal = "=${receipt?.chargeTotal?.roundUp()}??.\n"
        val ndsAmountTotal = "=${receipt?.ndsAmountTotal?.roundUp()}??.\n"
        val nspAmountTotal = "=${receipt?.nspAmountTotal?.roundUp()}??.\n"
        val total = "=${receipt?.total?.roundUp()}??."

        val subtotalText = "??????????\n"
        val discountTotalText = "????????????\n"
        val chargeTotalText = "??????????????\n"
        val ndsAmountTotalText = "${result?.ndsType?.ndsTypeValue}\n"
        val nspAmountTotalText = "${result?.nspType?.nspTypeValue}\n"
        val totalText = "??????????"

        if (receipt != null) {
            totalPrices.append(subtotal)
            totalPricesText.append(subtotalText)
            if (receipt.discountTotal.isNotZero()) {
                totalPrices.append(discountTotal)
                totalPricesText.append(discountTotalText)
            }
            if (receipt.chargeTotal.isNotZero()) {
                totalPrices.append(chargeTotal)
                totalPricesText.append(chargeTotalText)
            }
            totalPrices.append(ndsAmountTotal)
            totalPricesText.append(ndsAmountTotalText)
            totalPrices.append(nspAmountTotal)
            totalPricesText.append(nspAmountTotalText)
            totalPrices.append(total)
            totalPricesText.append(totalText)
        }
        return hashMapOf("texts" to totalPricesText, "prices" to totalPrices)
    }

    private fun retrieveAmountPaid(receipt: Receipt?): HashMap<String, String> {
        var amountPaidText = "????????????????:\n${receipt?.paymentType?.paymentTypeValue}"
        var amountPaidValue = "\n=${amountPaidVar}??."
        val change = amountPaidVar.subtract(receipt?.total!!.roundUp())
        vm.change.value = change
        if (change.isNotZero()) {
            amountPaidText += "\n??????????"
            amountPaidValue += "\n=${change}??."
        }
        return hashMapOf("amountPaidText" to amountPaidText, "amountPaidValue" to amountPaidValue)
    }

    private fun retrieveCashierInfo(result: Result?): StringBuilder {
        val builder = StringBuilder()
        builder.append("???????????? ${result?.cashRegisterId}\n")
        builder.append("?????? ${result?.inn}\n")
        builder.append("?????? ${result?.gnsRegNum}\n")
        builder.append("?????? ${result?.taxAccountingMethodName}\n")

        val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd/MM/yy HH:mm")
        val formattedDateTime = formatter.format(parser.parse(result?.receipt?.createdAt))

        builder.append("\n${formattedDateTime}\n")
        builder.append("\n${result?.receipt?.indexNum.toString().padStart(8, '0')} ${result?.receipt?.checkCode}")
        return builder
    }

    private fun generateQRCode(text: String): Bitmap {
        val width = 184
        val height = 184
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val codeWriter = MultiFormatWriter()
        try {
            val bitMatrix = codeWriter.encode(text, BarcodeFormat.QR_CODE, width, height)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        } catch (e: WriterException) {
            Log.e("QRCode writer", "generateQRCode: ${e.message}")
        }
        return bitmap
    }

    private fun navigateToPrintReceipt() {
        parentActivity.replaceFragment<PrintReceiptFragment>(R.id.sell_container, false)
    }

    companion object {
        fun newInstance() = SaveReceiptFragment()
    }
}