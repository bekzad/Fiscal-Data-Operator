package kg.nurtelecom.sell.ui.fragment.add_product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.AddProductFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.core.CoreFragment
import kg.nurtelecom.sell.ui.fragment.price_output.PriceOutputFragment
import kg.nurtelecom.sell.utils.addFragment
import kg.nurtelecom.sell.utils.getFragmentActivity

class AddProductFragment : CoreFragment<AddProductFragmentBinding>() {

    override val vm: SellMainViewModel by activityViewModels()

    override fun setupViews() {
        vb.productNotFromListButton.setOnClickListener {
            getFragmentActivity().addFragment(PriceOutputFragment.newInstance())
        }
    }

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        AddProductFragmentBinding.inflate(inflater, container, false)

    companion object {
        fun newInstance() = AddProductFragment()
    }
}
