package kg.nurtelecom.sell.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.nurtelecom.data.sell.Product
import kg.nurtelecom.sell.core.ItemClickListener
import kg.nurtelecom.sell.databinding.ProductListItemBinding
import kg.nurtelecom.sell.utils.isNotZero
import kg.nurtelecom.data.sell.Product

class ProductAdapter(
    private val productList: List<Product>,
    private val itemClick: ItemClickListener
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ProductListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
        holder.apply {
            binding.removeProductIv.setOnClickListener {
                itemClick.removeItem(position)
            }
        }
    }

    override fun getItemCount(): Int = productList.size

    class ProductViewHolder(val binding: ProductListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                productCountTv.text = fetchProductExpression(product)
                productSumTv.text = product.totalPrice.toString()
            }
        }

        private fun fetchProductExpression(product: Product): StringBuilder {
            val discount =
                if (product.discount.isNotZero()) (" - " + product.discount + "% ") else
                    ""
            val allowance =
                if (product.allowance.isNotZero()) ("+ " + product.allowance) + "%" else
                    ""

            return StringBuilder().also {
                it.append("${product.price} * ")
                it.append("${product.count.toInt()} ")
                it.append(discount)
                it.append(allowance)
            }
        }

        companion object {
            // TODO: must be changed
            fun getInstance(parent: ViewGroup) {

            }
        }

    }
}