package kg.nurtelecom.sell.ui.fragment.print_receipt

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.data.receipt.result.ReceiptItemResult
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.FragmentSaveReceiptBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.utils.isNotZero
import java.lang.StringBuilder

class SaveReceiptFragment : CoreFragment<FragmentSaveReceiptBinding, SellMainViewModel>(SellMainViewModel::class) {

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSaveReceiptBinding = FragmentSaveReceiptBinding.inflate(inflater, container, false)

    override fun setupToolbar(): Int = R.string.print_check

    override fun setupViews() {
        super.setupViews()

        vb.btnPrintCheck.setOnClickListener {
            navigateToPrintReceipt()
        }
    }

    override fun subscribeToLiveData() {
        // TO DO exception when I am trying to observe null object
        vm.fetchReceiptResult.observe(viewLifecycleOwner, { response ->
            val receiptNumber = response.result?.receipt?.indexNum.toString() ?: ""

            val itemBuilder = StringBuilder()
            val receiptItems = response.result?.receipt?.receiptItems
            if (!receiptItems.isNullOrEmpty()) {
                for (receipt in receiptItems) {
                    val receiptItem: String
                    with(receipt) {
                        receiptItem = "$productName       ${productUnitPrice}с. * $productQuantity = $subTotal"
                    }
                    itemBuilder.append(receiptItem)
                }
            }

            val builder = StringBuilder()
            val receipt = response.result?.receipt

            val subtotal = "ВСЕГО       =${receipt?.subtotal}\n"
            val discountTotal = "СКИДКА       =${receipt?.discountTotal}\n"
            val chargeTotal = "НАЦЕНКА       =${receipt?.chargeTotal}\n"
            val ndsAmountTotal = "НДС 12       =${receipt?.ndsAmountTotal}\n"
            val nspAmountTotal = "НСП 1       =${receipt?.nspAmountTotal}\n"
            val total = "ИТОГО       =${receipt?.total}"

            if (receipt != null) {
                builder.append(subtotal)
                if (receipt.discountTotal.isNotZero()) {
                    builder.append(discountTotal)
                }
                if (receipt.chargeTotal.isNotZero()) {
                    builder.append(chargeTotal)
                }
                builder.append(ndsAmountTotal)
                builder.append(nspAmountTotal)
                builder.append(total)
            }

            vb.tvTaxSalesPointName.text = response.result?.taxSalesPointName
            vb.tvReceiptNumber.text = getString(R.string.receipt_number, receiptNumber)
            vb.tvReceiptItems.text = itemBuilder
            vb.tvTotalPrices.text = builder
        })

    }

    private fun navigateToPrintReceipt() {
        parentActivity.replaceFragment<PrintReceiptFragment>(R.id.sell_container, true)
    }

    companion object {
        fun newInstance() = SaveReceiptFragment()
    }
}