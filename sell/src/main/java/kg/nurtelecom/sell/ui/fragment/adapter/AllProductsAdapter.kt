package kg.nurtelecom.sell.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.nurtelecom.data.sell.AllProducts
import kg.nurtelecom.sell.databinding.AllProductsItemListBinding

interface NavigationHost {
    fun navigateTo(allProducts: AllProducts)
}

class AllProductsAdapter(
    private val productList: List<AllProducts>,
    private val itemClick: NavigationHost
) : RecyclerView.Adapter<AllProductsAdapter.AllProductViewHolder>() {

    class AllProductViewHolder(val binding: AllProductsItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(product: AllProducts) {
                binding.apply {
                    productNameTv.text = product.productName
                    productPriceTv.text = product.productPrice.toString()
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllProductViewHolder {
        val binding = AllProductsItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllProductViewHolder, position: Int) {
        val product = productList[position]
        holder.apply {
            bind(product)
            binding.navigateIv.setOnClickListener { itemClick.navigateTo(product) }
        }
    }

    override fun getItemCount() = productList.size
}