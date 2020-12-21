package kg.nurtelecom.sell.ui.fragment.add_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kg.nurtelecom.core.fragment.CoreFragment
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.INavigation
import kg.nurtelecom.sell.databinding.AddProductFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.fragment.price_output.PriceOutputFragment

class AddProductFragment : Fragment()
    /*CoreFragment<AddProductFragmentBinding, SellMainViewModel>(SellMainViewModel::class)*/,
    INavigation {

    companion object {
        fun newInstance() = AddProductFragment()
    }

    private lateinit var vb: AddProductFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = AddProductFragmentBinding.inflate(inflater, container, false)
        setupViews()
        return vb.root
    }

    private fun setupViews() {
        vb.productNotFromListButton.setOnClickListener {
            navigate()
        }
    }

    override fun navigate() {
        val activity = activity as AppCompatActivity
        activity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, PriceOutputFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("onDestroyView() !")
    }
}