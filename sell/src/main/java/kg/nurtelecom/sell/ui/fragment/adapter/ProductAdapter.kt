package kg.nurtelecom.sell.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.nurtelecom.data.sell.Product
import kg.nurtelecom.sell.core.ProductItemClickListener
import kg.nurtelecom.sell.databinding.ProductListItemBinding
import kg.nurtelecom.sell.utils.isNotZero
import kg.nurtelecom.data.sell.Product

class ProductAdapter(private val itemClick: ProductItemClickListener) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    var productList: List<Product> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder.getInstance(parent, itemClick)

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        if (position in productList.indices) {
            holder.bind(productList[position], position)
        }
    }

    override fun getItemCount(): Int = productList.size

    class ProductViewHolder(
        private val binding: ProductListItemBinding,
        private val itemClick: ProductItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product, position: Int) {
            binding.apply {
                productCountTv.text = fetchProductExpression(product)
                productSumTv.text = product.totalPrice.toString()
                removeProductIv.setOnClickListener { itemClick.removeItem(position) }
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
            fun getInstance(parent: ViewGroup, itemClick: ProductItemClickListener): ProductViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ProductListItemBinding.inflate(layoutInflater, parent, false)
                return ProductViewHolder(binding, itemClick)
            }
        }
    }
}