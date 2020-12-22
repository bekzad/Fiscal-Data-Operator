package kg.nurtelecom.sell.ui.fragment.add_product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.AddProductFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.core.CoreFragment
import kg.nurtelecom.sell.ui.fragment.price_output.PriceOutputFragment

class AddProductFragment : CoreFragment<AddProductFragmentBinding>() {

    override val vm: SellMainViewModel by activityViewModels()

    override fun setupViews() {
        vb.productNotFromListButton.setOnClickListener { navigate() }
    }

    private fun navigate() {
        val activity = activity as AppCompatActivity
        activity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, PriceOutputFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): AddProductFragmentBinding = AddProductFragmentBinding.inflate(inflater, container, false)

    companion object {
        fun newInstance() = AddProductFragment()
    }
}
