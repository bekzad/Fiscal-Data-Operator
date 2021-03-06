package kg.nurtelecom.sell.ui.fragment.payment_method

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.gson.Gson
import kg.nurtelecom.core.extension.enable
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.core.extension.visible
import kg.nurtelecom.data.enums.OperationType
import kg.nurtelecom.data.enums.PaymentType
import kg.nurtelecom.data.receipt.request.FetchReceiptRequest
import kg.nurtelecom.data.receipt.request.ReceiptItemRequest
import kg.nurtelecom.data.sell.Product
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.FragmentPaymentByCashBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.fragment.print_receipt.SaveReceiptFragment
import kg.nurtelecom.sell.utils.isGreaterThanOrEqualTo
import java.math.BigDecimal

class PaymentByCashFragment : CoreFragment<FragmentPaymentByCashBinding, SellMainViewModel>(SellMainViewModel::class) {

    private var amountPaidVar = BigDecimal.ZERO
    private var canContinue = true

    override fun createViewBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ): FragmentPaymentByCashBinding = FragmentPaymentByCashBinding.inflate(inflater, container, false)

    override fun setupToolbar(): Int = R.string.payment_method

    override fun setupViews() {
        setupPaymentMode()

        vb.btnContinue.setOnClickListener {
            if (canContinue) {
                navigateToSaveReceipt()
                fetchReceipt()
            } else {
                showErrorDialogBox()
            }
        }

        vb.icReceived.apply {
            fetchTextState {
                if (it.isNullOrEmpty()) {
                    setupButtons()
                    amountPaidVar = vm.taxSum.value!!
                } else {
                    // Amount paid will change only if user enters something
                    amountPaidVar = BigDecimal(it.toString())
                    vb.btnContinue.text = getString(R.string.pay_cash)
                    vb.btnContinue.enable(true)
                }
                // Can continue to the next fragment only if paid amount is greater than or equal to sumWithTaxes
                canContinue = amountPaidVar.isGreaterThanOrEqualTo(vm.taxSum.value!!)
            }
        }
    }

    // By default amount paid is equal to the sum with taxes
    // Because we want to pass to the next fragment if there is no value
    override fun subscribeToLiveData() {
        vm.taxSum.observe(viewLifecycleOwner) { taxSum ->
            taxSum.apply {
                vb.icSum.setContent(this)
                vb.icReceived.setHint(this)
                amountPaidVar = this
            }
        }
    }

    private fun navigateToSaveReceipt() {
        vm.amountPaid.value = amountPaidVar
        parentActivity.replaceFragment<SaveReceiptFragment>(R.id.sell_container, false)
    }

    private fun fetchReceipt() {
        // Clear the old receipt before going to the next fragment
        vm.fetchReceiptResult.value = null

        val receiptItems: MutableList<ReceiptItemRequest> = mutableListOf()
        var productList: List<Product> = listOf()

        vm.productList.observe(viewLifecycleOwner, {
            productList = it
        })

        for ((index, product) in productList.withIndex()) {
            val itemIndex: Long = (index + 1).toLong()
            val name: String = if (product.productName.isEmpty()) {
                "??????????????"
            } else product.productName
            val itemRequest = ReceiptItemRequest(product.productId, name, product.productQuantity, product.productUnitPrice,
                    product.discount, product.charge, itemIndex)
            receiptItems.add(itemRequest)
        }

        // Create a request and convert it to Json
        val fetchReceiptRequest = FetchReceiptRequest(OperationType.SALE, PaymentType.CASH, receiptItems)
        val gson = Gson()
        val jsonString = gson.toJson(fetchReceiptRequest)

        vm.fetchReceipt(jsonString)
    }
    
    private fun setupButtons() {
        when (vm.operationType) {
            OperationType.POSTPAY -> vb.btnContinue.text = getString(R.string.text_no_deposit)
            OperationType.PREPAY -> vb.btnContinue.text = getString(R.string.btn_continue)
            OperationType.SALE -> vb.btnContinue.text = getString(R.string.without_change)
        }
    }

    private fun setupPaymentMode() {
        when (vm.operationType) {
            OperationType.PREPAY -> {
                vb.icSum.visible(false)
                vb.btnContinue.enable(false)
            }
        }
        setupButtons()
    }

    private fun showErrorDialogBox() {
        val inflater = requireActivity().layoutInflater;
        AlertDialog.Builder(context)
                .setCustomTitle(inflater.inflate(R.layout.error_dialog, null))
                .setPositiveButton(android.R.string.ok) { dialog, which -> }
                .setMessage(R.string.dialog_error)
                .show()
    }

    companion object {
        fun newInstance() = PaymentByCashFragment()
    }
}