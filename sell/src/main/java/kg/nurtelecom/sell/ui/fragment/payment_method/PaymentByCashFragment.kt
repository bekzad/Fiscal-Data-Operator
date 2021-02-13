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

        // We are changing the value in viewModel before continuing
        // NSP is added to the value of taxSum
        vb.btnContinue.setOnClickListener {
            if (canContinue) {
                vm.taxSum.value = vm.sumWithNSP.value
                vm.amountPaid.value = amountPaidVar
                navigateToSaveReceipt()
                fetchReceipt()
            } else {
                // TO Do error dialog box that entered amount is less than what is required
            }
        }

        vb.icReceived.apply {
            fetchTextState {
                if (it.isNullOrEmpty()) {
                    setupButtons()
                } else {
                    // Amount paid will change only if user enters something
                    amountPaidVar= BigDecimal(it.toString())
                    vb.btnContinue.text = getString(R.string.pay_cash)
                    vb.btnContinue.enable(true)
                }
                // Can continue to the next fragment only if paid amount is greater than or equal to sumWithTaxes
                canContinue = amountPaidVar.isGreaterThanOrEqualTo(vm.sumWithNSP.value!!)
            }
        }
    }

    // By default amount paid is equal to the sum with taxes
    // Because we want to pass to the next fragment if there is no value
    override fun subscribeToLiveData() {
        vm.sumWithNSP.observe(viewLifecycleOwner) { sumWithNSP ->
            sumWithNSP.apply {
                vb.icSum.setContent(this)
                vb.icReceived.setHint(this)
                amountPaidVar = this
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
            val productName = "Позиция"
            val discount = product.discount
            val charge = product.allowance
            val itemRequest = ReceiptItemRequest(null, productName, product.count,
                    product.price, discount, charge, itemIndex.toLong())
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

    companion object {
        fun newInstance() = PaymentByCashFragment()
    }
}