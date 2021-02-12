package kg.nurtelecom.sell.ui.fragment.print_receipt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.data.receipt.result.FetchReceiptResult
import kg.nurtelecom.data.receipt.result.Receipt
import kg.nurtelecom.data.receipt.result.ReceiptItemResult
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.FragmentSaveReceiptBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.utils.isNotZero

class SaveReceiptFragment : CoreFragment<FragmentSaveReceiptBinding, SellMainViewModel>(SellMainViewModel::class) {

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
        // TO DO exception when I am trying to observe null object only when the code is 401
        vm.fetchReceiptResult.observe(viewLifecycleOwner, { response ->
            updateScreen(response)
            vb.clReceiptContainer.visibility = View.VISIBLE
        })
    }

    private fun updateScreen(response: FetchReceiptResult) {
        // Get the receipt
        val receipt = response.result?.receipt
        val receiptNumber = receipt?.indexNum.toString()
        val receiptItems = receipt?.receiptItems

        // Get the receiptItems and totalPrices as one string to assign to a TextView
        val receiptItemsString = retrieveReceiptItems(receiptItems)
        val totalPricesString = retrieveTotalPrices(receipt)

        vm.amountPaid.observe(viewLifecycleOwner, { amountPaid ->
            var amountPaidText = "Получено:\nНАЛИЧНЫЕ"
            var amountPaidValue = "\n=${amountPaid}с."
            val change = amountPaid.subtract(receipt?.total)
            if (change.isNotZero()) {
                amountPaidText += "\nСДАЧА"
                amountPaidValue += "\n=${change}с."
            }
            vb.tvAmountPaidText.text = amountPaidText
            vb.tvAmountPaid.text =amountPaidValue
        })

        // Set the values of textViews
        vb.tvTaxSalesPointName.text = response.result?.taxSalesPointName
        vb.tvReceiptNumber.text = getString(R.string.receipt_number, receiptNumber)
        vb.tvProductName.text = receiptItemsString["names"]
        vb.tvReceiptItems.text = receiptItemsString["items"]
        vb.tvTotalPricesText.text = totalPricesString["texts"]
        vb.tvTotalPrices.text = totalPricesString["prices"]
    }

    private fun retrieveReceiptItems(receiptItems: List<ReceiptItemResult>?): Map<String, StringBuilder> {
        val nameBuilder = StringBuilder()
        val itemBuilder = StringBuilder()

        if (!receiptItems.isNullOrEmpty()) {
            for (receipt in receiptItems) {
                var productNames: String
                val receiptItem: String
                with(receipt) {
                    productNames = "$productName\n" // TO DO other things beside the name exist
                    receiptItem = "${productUnitPrice}с. * $productQuantity = ${subtotal}с.\n" // To do Quantity can be meters or kg
                }
                nameBuilder.append(productNames)
                itemBuilder.append(receiptItem)
            }
        }
        return mapOf("names" to nameBuilder, "items" to itemBuilder)
    }

    private fun retrieveTotalPrices(receipt: Receipt?): Map<String, StringBuilder> {

        val totalPrices = StringBuilder()
        val totalPricesText = StringBuilder()

        val subtotal = "=${receipt?.subtotal}с.\n"
        val discountTotal = "=${receipt?.discountTotal}с.\n"
        val chargeTotal = "=${receipt?.chargeTotal}с.\n"
        val ndsAmountTotal = "=${receipt?.ndsAmountTotal}с.\n"
        val nspAmountTotal = "=${receipt?.nspAmountTotal}с.\n"
        val total = "=${receipt?.total}с."

        val subtotalText = "ВСЕГО\n"
        val discountTotalText = "СКИДКА\n"
        val chargeTotalText = "НАЦЕНКА\n"
        val ndsAmountTotalText = "НДС 12\n" // TO DO not always 12
        val nspAmountTotalText = "НСП 1\n" // TO DO not always 1
        val totalText = "ИТОГО"

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
        return mapOf("texts" to totalPricesText, "prices" to totalPrices)
    }

    private fun navigateToPrintReceipt() {
        parentActivity.replaceFragment<PrintReceiptFragment>(R.id.sell_container, true)
    }

    companion object {
        fun newInstance() = SaveReceiptFragment()
    }
}