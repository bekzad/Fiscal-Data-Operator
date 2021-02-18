package kg.nurtelecom.sell.ui.fragment.payment_method

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
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
import kg.nurtelecom.sell.databinding.FragmentPaymentByCardBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.fragment.print_receipt.SaveReceiptFragment
import java.math.BigDecimal


class PaymentByCardFragment : CoreFragment<FragmentPaymentByCardBinding, SellMainViewModel>(SellMainViewModel::class) {

    private var amountPaidVar = BigDecimal.ZERO

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentByCardBinding = FragmentPaymentByCardBinding.inflate(inflater, container, false)

    override fun setupToolbar(): Int = R.string.payment_method

    override fun setupViews() {
        setupButtons()
        vb.btnContinueCard.setOnClickListener {
            fetchReceipt()
            navigateToSaveReceipt()
        }
    }

    override fun subscribeToLiveData() {
        vm.taxSum.observe(viewLifecycleOwner) { sum ->
            vb.icSumCard.setContent(sum)
            amountPaidVar = sum
        }
    }

    private fun setupButtons() {
        when (vm.operationType) {
            OperationType.SALE -> {
                vb.btnContinueCard.text = getString(R.string.btn_continue)
                vb.icReceivedCard.setContent(vm.taxSum.value ?: BigDecimal.ZERO)
            }
            OperationType.POSTPAY -> {
                setupCreditMode()
            }
            OperationType.PREPAY -> {
                setupPrepayMode()
            }
        }
    }

    private fun setupCreditMode() {
        vb.btnContinueCard.text = getString(R.string.text_no_deposit)
        vb.icReceivedCard.apply {
            setIsEditable(true)
            eraseContent()
        }
        vb.icReceivedCard.fetchTextState {
            if (!it.isNullOrEmpty()) {
                vb.btnContinueCard.text = getString(R.string.btn_continue)
            } else {
                vb.btnContinueCard.text = getString(R.string.text_no_deposit)
            }
        }
    }

    private fun setupPrepayMode() {
        vb.icSumCard.visible(false)
        vb.icReceivedCard.apply {
            setIsEditable(true)
            eraseContent()
        }
        vb.btnContinueCard.enable(false)
        vb.icReceivedCard.fetchTextState {
            vb.btnContinueCard.enable(!it.isNullOrEmpty())
        }
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
                "Позиция"
            } else product.productName
            val itemRequest = ReceiptItemRequest(product.productId, name, product.productQuantity, product.productUnitPrice,
                    product.discount, product.charge, itemIndex)
            receiptItems.add(itemRequest)
        }

        // Create a request and convert it to Json
        val fetchReceiptRequest = FetchReceiptRequest(OperationType.SALE, PaymentType.CASHLESS, receiptItems)
        val gson = Gson()
        val jsonString = gson.toJson(fetchReceiptRequest)

        vm.fetchReceipt(jsonString)
    }

    private fun navigateToSaveReceipt() {
        vm.amountPaid.value = amountPaidVar
        parentActivity.replaceFragment<SaveReceiptFragment>(R.id.sell_container, false)
    }

    companion object {
        fun newInstance() = PaymentByCardFragment()
    }
}