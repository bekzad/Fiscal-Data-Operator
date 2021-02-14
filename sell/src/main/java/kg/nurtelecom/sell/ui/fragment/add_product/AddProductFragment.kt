package kg.nurtelecom.sell.ui.fragment.add_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import kg.nurtelecom.core.extension.parentActivity
import kg.nurtelecom.core.extension.replaceFragment
import kg.nurtelecom.ofd.item_decoration.RoundDecor
import kg.nurtelecom.data.sell.Products
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.CoreFragment
import kg.nurtelecom.sell.core.ItemClickListener
import kg.nurtelecom.sell.databinding.AddProductFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.fragment.adapter.ProductCategoryAdapter
import kg.nurtelecom.sell.ui.fragment.price_output.PriceOutputFragment
import kg.nurtelecom.sell.utils.doOnMenuItemCollapse
import kg.nurtelecom.sell.utils.doOnQueryTextChange


class AddProductFragment : CoreFragment<AddProductFragmentBinding, SellMainViewModel>(SellMainViewModel::class),  ItemClickListener {

    private val catalogAdapter: ProductCategoryAdapter = ProductCategoryAdapter(this)

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        AddProductFragmentBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.fetchProductCatalogLocally()
    }

    override fun setupToolbar(): Int = R.string.product_selection

    override fun setupViews() {
        setHasOptionsMenu(true)
        setupRV()
        setupNavigation()
    }

    private fun setupRV() {
        vb.allProductsRv.adapter = catalogAdapter
        vb.allProductsRv.addItemDecoration(RoundDecor())
    }

    override fun subscribeToLiveData() {
        vm.productCatalog.observe(viewLifecycleOwner, { product ->
            catalogAdapter.addHeaderAndSubmitList(product)
        })
        vm.filteredProducts?.observe(viewLifecycleOwner) { sortedProducts ->
            catalogAdapter.addHeaderAndSubmitList(null, sortedList = sortedProducts)
        }
    }

    override fun transferData(product: Products) {
        vm.sendSelectedProduct(product)
        navigateToPriceOutputFragment()
    }

    private fun navigateToPriceOutputFragment() {
        parentActivity.replaceFragment<PriceOutputFragment>(R.id.sell_container)
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
