package kg.nurtelecom.sell.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.nurtelecom.sell.databinding.ProductListItemBinding
import java.math.BigDecimal

class ProductAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ProductListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = productList.size


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
                if (product.discount != null && isNotZero(product.discount)) (" + " + product.discount + "% ") else
                    ""
            val allowance =
                if (product.allowance != null && isNotZero(product.allowance)) ("+ " + product.allowance) + "%" else
                    ""
            productExpressionLine.apply {
                append("${product.price} * ")
                append("${product.count.toInt()}")
                append(discount)
                append(allowance)
            }
            return productExpressionLine
        }
        private fun isNotZero(value: BigDecimal): Boolean {
            return value.compareTo(BigDecimal.ZERO) != 0
        }
    }
}
