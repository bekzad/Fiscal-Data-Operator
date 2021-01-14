package kg.nurtelecom.sell.ui.fragment.add_product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.data.sell.AllProducts
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.AddProductFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.fragment.adapter.AllProductsAdapter
import kg.nurtelecom.sell.ui.fragment.adapter.NavigationHost
import kg.nurtelecom.sell.ui.fragment.price_output.PriceOutputFragment


class AddProductFragment : CoreFragment<AddProductFragmentBinding>(), NavigationHost {

    private lateinit var allProductsAdapter: AllProductsAdapter
    override val vm: SellMainViewModel by activityViewModels()

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        AddProductFragmentBinding.inflate(inflater, container, false)

    override fun setupToolbar(): Int = R.string.product_selection

    override fun setupViews() {
        vm.allProducts.value?.let { setupRV(it) }
        setupNavigation()
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        vm.allProducts.observe(viewLifecycleOwner, {
            allProductsAdapter.notifyItemInserted(it.lastIndex)
        })
    }

    override fun navigateToPriceOutputFragment(allProducts: AllProducts) {
        parentActivity.replaceFragment<PriceOutputFragment>(R.id.sell_container, true)
        vm.sendSelectedProduct(allProducts)
    }

    private fun setupRV(allProducts: MutableList<AllProducts>) {
        vb.allProductsRv.apply {
            allProductsAdapter = AllProductsAdapter(allProducts, this@AddProductFragment)
            adapter = allProductsAdapter
        }
    }

    private fun setupNavigation() {
        vb.productNotFromListButton.setOnClickListener {
            parentActivity.replaceFragment<PriceOutputFragment>(R.id.sell_container, true)
        }
    }

    companion object {
        fun newInstance() = AddProductFragment()
    }
}
