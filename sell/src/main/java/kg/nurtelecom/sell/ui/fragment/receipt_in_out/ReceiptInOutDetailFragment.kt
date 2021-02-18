package kg.nurtelecom.sell.ui.fragment.receipt_in_out

import kg.nurtelecom.core.fragment.SimpleFragment
import kg.nurtelecom.data.receipt_in_out.ReceiptInOutResult
import kg.nurtelecom.sell.databinding.FragmentReceiptInOutDetailBinding

class ReceiptInOutDetailFragment : SimpleFragment<FragmentReceiptInOutDetailBinding>() {

    override fun setupViews() {
        super.setupViews()
        val receipt = arguments?.get("receipt")
        vb.tvReceiptDetail.text = (receipt as ReceiptInOutResult).toString()
    }

    override fun getBinding(): FragmentReceiptInOutDetailBinding {
        return FragmentReceiptInOutDetailBinding.inflate(layoutInflater)
    }

    companion object {
        fun newInstance(): ReceiptInOutDetailFragment {
            return ReceiptInOutDetailFragment()
        }
    }
}