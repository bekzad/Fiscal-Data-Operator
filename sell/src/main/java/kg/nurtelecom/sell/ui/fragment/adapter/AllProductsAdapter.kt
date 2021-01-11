package kg.nurtelecom.sell.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.nurtelecom.data.sell.AllProducts
import kg.nurtelecom.sell.databinding.AllProductsItemListBinding

interface NavigationHost {
    fun navigateToPriceOutputFragment(allProducts: AllProducts)
}

class AllProductsAdapter(
    private val productList: List<AllProducts>,
    private val itemClick: NavigationHost
) : RecyclerView.Adapter<AllProductsAdapter.AllProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllProductViewHolder {
        return AllProductViewHolder.getInstance(parent, itemClick)
    }

    override fun onBindViewHolder(holder: AllProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount() = productList.size

    class AllProductViewHolder(
        private val binding: AllProductsItemListBinding,
        private val itemClick: NavigationHost
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: AllProducts) {
            binding.apply {
                cellView.apply {
                    setTitle(product.productName)
                    setSubTitle(product.productPrice.toString())
                    setOnCellClickListener {
                        it.setOnClickListener {
                            itemClick.navigateToPriceOutputFragment(product)
                        }
                    }
                }
            }
        }

        companion object {
            fun getInstance(parent: ViewGroup, itemClick: NavigationHost): AllProductViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AllProductsItemListBinding.inflate(layoutInflater, parent, false)
                return AllProductViewHolder(binding, itemClick)
            }
        }
    }
}