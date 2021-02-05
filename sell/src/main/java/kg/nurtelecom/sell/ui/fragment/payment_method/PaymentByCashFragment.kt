package kg.nurtelecom.sell.ui.fragment.payment_method

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
import kg.nurtelecom.sell.utils.roundUp
import java.math.BigDecimal

class PaymentByCashFragment : CoreFragment<FragmentPaymentByCashBinding, SellMainViewModel>(SellMainViewModel::class) {

    private lateinit var sumWithNSP: BigDecimal

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentByCashBinding = FragmentPaymentByCashBinding.inflate(inflater, container, false)

    override fun setupToolbar(): Int = R.string.payment_method

    override fun setupViews() {
        // We are changing the value in viewModel before continuing
        // NSP is added to the value of taxSum
        setupPaymentMode()

        vb.btnContinue.setOnClickListener {
            vm.taxSum.value = sumWithNSP
            navigateToSaveReceipt()
            fetchReceipt()
        }

        vb.icReceived.apply {
            fetchTextState {
                if (it.isNullOrEmpty()) {
                    vb.btnContinue.text = getString(R.string.without_change)
                    setupButtons()
                } else {
                    vb.btnContinue.text = getString(R.string.pay_cash)
                    vb.btnContinue.enable(true)
                }
            }
        }
    }

    // We are adding NSP and showing to the user the result
    override fun subscribeToLiveData() {
        val taxNSP = BigDecimal("1.01")
        vm.taxSum.observe(viewLifecycleOwner) { sum ->
            sumWithNSP = sum.multiply(taxNSP).roundUp()
            sumWithNSP.apply {
                vb.icSum.setContent(this)
                vb.icReceived.setHint(this)
            }
        }
    }

    private fun navigateToSaveReceipt() {
        parentActivity.replaceFragment<SaveReceiptFragment>(R.id.sell_container, true)
    }

    private fun fetchReceipt() {
        val receiptItems: MutableList<ReceiptItemRequest> = mutableListOf()
        var productList: List<Product> = listOf()

        vm.productList.observe(viewLifecycleOwner, {
            productList = it
        })

        // Here we are sending id as just null
        for ((index, product) in productList.withIndex()) {
            val itemIndex = index + 1
            val itemRequest = ReceiptItemRequest(null, product.count, product.totalPrice, itemIndex.toLong())
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

    private fun navigateToPrintCheck() {
        // TO DO inflates the print check fragment
    }

    companion object {
        fun newInstance() = PaymentByCashFragment()
    }
}