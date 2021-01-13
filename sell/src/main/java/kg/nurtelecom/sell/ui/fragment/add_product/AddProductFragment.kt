package kg.nurtelecom.sell.ui.fragment.add_product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.data.sell.AllProducts
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.AddProductFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.fragment.adapter.AllProductsAdapter
import kg.nurtelecom.sell.ui.fragment.adapter.NavigationHost
import kg.nurtelecom.sell.ui.fragment.price_output.PriceOutputFragment
import kg.nurtelecom.sell.utils.replaceFragment


class AddProductFragment : CoreFragment<AddProductFragmentBinding>(), NavigationHost {

    private lateinit var allProductsAdapter: AllProductsAdapter
    override val vm: SellMainViewModel by activityViewModels()

    override fun setupViews() {
        vm.allProducts.value?.let { setupRV(it) }
        vb.productNotFromListButton.setOnClickListener {
            replaceFragment(R.id.sell_container, PriceOutputFragment.newInstance(), true)
        }
    }

    private fun setupRV(allProducts: MutableList<AllProducts>) {
        vb.allProductsRv.apply {
            allProductsAdapter = AllProductsAdapter(allProducts, this@AddProductFragment)
            adapter = allProductsAdapter
        }
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        vm.allProducts.observe(viewLifecycleOwner, {
            allProductsAdapter.notifyItemInserted(it.lastIndex)
        })
    }

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        AddProductFragmentBinding.inflate(inflater, container, false)

    override fun navigateToPriceOutputFragment(allProducts: AllProducts) {
        replaceFragment(R.id.sell_container, PriceOutputFragment.newInstance(), true)
        vm.sendSelectedProduct(allProducts)
    }

    companion object {
        fun newInstance() = AddProductFragment()
    }
}
