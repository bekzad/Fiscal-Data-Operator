package kg.nurtelecom.sell.ui.fragment.add_product

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.data.sell.AllProducts
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.databinding.AddProductFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.fragment.adapter.ProductCategoryAdapter
import kg.nurtelecom.sell.ui.fragment.price_output.PriceOutputFragment
import kg.nurtelecom.sell.utils.doOnMenuItemCollapse
import kg.nurtelecom.sell.utils.doOnQueryTextChange


class AddProductFragment : CoreFragment<AddProductFragmentBinding>() {

    private val catalogAdapter: ProductCategoryAdapter = ProductCategoryAdapter()
    override val vm: SellMainViewModel by activityViewModels()

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        AddProductFragmentBinding.inflate(inflater, container, false)

    override fun setupToolbar(): Int = R.string.product_selection

    override fun setupViews() {
        setHasOptionsMenu(true)
        setupRV()
        setupNavigation()
    }

    private fun setupRV() {
        vb.allProductsRv.adapter = catalogAdapter
    }

    override fun subscribeToLiveData() {
        vm.productCatalog.observe(viewLifecycleOwner, { product ->
            catalogAdapter.addHeaderAndSubmitList(product)
        })
        vm.filteredProducts?.observe(viewLifecycleOwner) { sortedProducts ->
            catalogAdapter.addHeaderAndSubmitList(null, sortedList = sortedProducts)
        }
    }

    private fun navigateToPriceOutputFragment(allProducts: AllProducts) {
        parentActivity.replaceFragment<PriceOutputFragment>(R.id.sell_container)
        vm.sendSelectedProduct(allProducts)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sell_menu, menu)
        val search = menu.findItem(R.id.ic_search)
        val searchView: SearchView = search.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.queryHint = getString(R.string.text_search)
        searchProduct(searchView)
        search.doOnMenuItemCollapse {
            catalogAdapter.addHeaderAndSubmitList(vm.productCatalog.value, null)
            true
        }
    }

    private fun searchProduct(searchView: SearchView) {
        searchView.doOnQueryTextChange { query ->
            vm.searchProduct(query)
            true
        }
    }

    private fun setupNavigation() {
        vb.productNotFromListButton.setOnClickListener {
            parentActivity.replaceFragment<PriceOutputFragment>(R.id.sell_container)
        }
    }

    companion object {
        fun newInstance(): AddProductFragment = AddProductFragment()
    }
}
