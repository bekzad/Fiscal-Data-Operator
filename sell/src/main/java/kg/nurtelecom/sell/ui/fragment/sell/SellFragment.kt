package kg.nurtelecom.sell.ui.fragment.sell

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.core.INavigation
import kg.nurtelecom.sell.databinding.SellFragmentBinding
import kg.nurtelecom.sell.ui.activity.SellMainViewModel
import kg.nurtelecom.sell.ui.fragment.adapter.Product
import kg.nurtelecom.sell.ui.fragment.adapter.ProductAdapter
import kg.nurtelecom.sell.ui.fragment.add_product.AddProductFragment

class SellFragment : Fragment()/*CoreFragment<SellFragmentBinding, SellMainViewModel>(SellMainViewModel::class)*/,
    INavigation {

    companion object {
        fun newInstance() = SellFragment()
    }

    private lateinit var productAdapter: ProductAdapter
    private val vms: SellMainViewModel by activityViewModels()
    private lateinit var vb: SellFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = SellFragmentBinding.inflate(inflater, container, false)
        setupViews()
        return vb.root
    }

    private fun setupRV(product: List<Product>) {
        vb.productRv.apply {
            productAdapter = ProductAdapter(product)
            adapter = productAdapter
        }
    }

    private fun setupViews() {
        setupRV(listOf())
        setupTaxView()
        navigate()
        observeProduct()
    }

    private fun setupTaxView() {
        vb.sumPayCv.apply {
            setCardTitle(R.string.sum_pay)
            addNextIcon()
            setBackground(R.drawable.mask)
        }
        vms.calculateTaxSum().observe(viewLifecycleOwner) {
            vb.sumPayCv.setCardContent(it)
        }
    }

    private fun observeProduct() {
        vms.productList.observe(viewLifecycleOwner) { product ->
            setupRV(product)
        }
    }

    override fun navigate() {
        vb.addProductButton.setOnClickListener {
            val activity = activity as AppCompatActivity
            activity.supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, AddProductFragment.newInstance())
                .commit()
        }
    }
}