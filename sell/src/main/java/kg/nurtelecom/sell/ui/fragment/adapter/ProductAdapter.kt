package kg.nurtelecom.sell.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.nurtelecom.sell.databinding.ProductListItemBinding

class ProductAdapter(private var productList: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: ProductListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                productCountTv.text = fetchProductExpression(product)
                productSumTv.text = product.totalPrice.toString()
            }
        }

        private fun fetchProductExpression(product: Product): StringBuilder {
            val productExpressionLine = StringBuilder()
            val discount =
                if (product.discount != null) (" + " + product.discount) + "% " else
                    ""
            val allowance =
                if (product.allowance != null) ("+ " + product.allowance) + "%" else
                    ""
            productExpressionLine.apply {
                append("${product.price} * ")
                append("${product.count.toInt()}")
                append(discount)
                append(allowance)
            }
            return productExpressionLine
        }

        private fun fetchProductSumWithDiscount(product: Product): Double {
            val sumOfProduct = (product.price * product.count)
            val discount = (product.discount?.div(100)) ?: 0.0
            val allowance = (product.allowance?.div(100)) ?: 0.0
            return sumOfProduct - (sumOfProduct * discount) + (sumOfProduct * allowance)
        }
    } // RecyclerViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ProductListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
