package kg.nurtelecom.sell.ui.fragment.add_product

import android.graphics.Color
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.data.sell.AllProducts
import kg.nurtelecom.data.sell.Result
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.AddProductFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.fragment.adapter.NavigationHost
import kg.nurtelecom.sell.ui.fragment.adapter.ProductCategoryAdapter
import kg.nurtelecom.sell.ui.fragment.price_output.PriceOutputFragment


class AddProductFragment : CoreFragment<AddProductFragmentBinding>(), NavigationHost {

    private val allProductsAdapter: ProductCategoryAdapter = ProductCategoryAdapter()
    override val vm: SellMainViewModel by activityViewModels()

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        AddProductFragmentBinding.inflate(inflater, container, false)

    override fun setupToolbar(): Int = R.string.product_selection

    override fun setupViews() {
        setHasOptionsMenu(true)
        setupNavigation()
        vb.allProductsRv.adapter = allProductsAdapter
        vb.allProductsRv.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun subscribeToLiveData() {
        vm.productCategory.observe(viewLifecycleOwner, { product ->
            allProductsAdapter.addHeaderAndSubmitList(product)
        })
    }

    override fun navigateToPriceOutputFragment(allProducts: AllProducts) {
        parentActivity.replaceFragment<PriceOutputFragment>(R.id.sell_container)
        vm.sendSelectedProduct(allProducts)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.sell_menu, menu)
        val search = menu.findItem(R.id.ic_search)
        val searchView = MenuItemCompat.getActionView(search) as SearchView
        searchView.queryHint = getString(R.string.text_search)
        val editText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        editText.setTextColor(Color.WHITE)
        editText.setHintTextColor(Color.WHITE)
    }

    // TODO("Move into VM")
    private fun searchProduct(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean = false
            override fun onQueryTextChange(newText: String): Boolean {
                //allProductsAdapter.filter.filter(newText)
                return true
            }
        })
    }

    private fun setupNavigation() {
        vb.productNotFromListButton.setOnClickListener {
            parentActivity.replaceFragment<PriceOutputFragment>(R.id.sell_container)
        }
    }

    companion object {
        fun newInstance() = AddProductFragment()
    }
}
