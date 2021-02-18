package kg.nurtelecom.sell.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.nurtelecom.data.sell.Product
import kg.nurtelecom.sell.core.ItemClickListener
import kg.nurtelecom.sell.R
import kg.nurtelecom.sell.databinding.ProductListItemBinding
import kg.nurtelecom.sell.utils.isNotZero
import kg.nurtelecom.sell.utils.roundUp
import java.math.BigDecimal

class ProductAdapter(private val itemClick: ItemClickListener) :
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
        private val itemClick: ItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product, position: Int) {
            binding.apply {
                if (product.productName.isEmpty()) {
                    tvProductPosition.text =
                        root.resources.getString(R.string.position_with_n, position + 1)
                } else tvProductPosition.text = product.productName

                tvProductCount.text = fetchProductExpression(product)
                ivRemoveProduct.setOnClickListener { itemClick.onItemClick(position) }
                tvProductSum.text = String.format(calculateSum(product).toString())
            }
        }

        private fun fetchProductExpression(product: Product): StringBuilder {
            val productExpressionLine = StringBuilder()
            val discount =
                if (product.discount.isNotZero()) ("- ${product.discount}% ") else
                    ""
            val allowance =
                if (product.charge.isNotZero()) ("+ ${product.charge}%") else
                    ""
            productExpressionLine.apply {
                append("${product.productUnitPrice.roundUp()} * ")
                append("${product.productQuantity} ")
                append(discount)
                append(allowance)
            }
            return productExpressionLine
        }

        private fun calculateSum(product: Product): BigDecimal {
            val totalPrice = product.productUnitPrice.multiply(product.productQuantity)
            val hundred = BigDecimal("100.0")
            val discount = totalPrice.multiply(product.discount).divide(hundred)
            val allowance = totalPrice.multiply(product.charge).divide(hundred)
            return totalPrice.subtract(discount).add(allowance).roundUp()
        }

        companion object {
            fun getInstance(parent: ViewGroup, itemClick: ItemClickListener): ProductViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ProductListItemBinding.inflate(layoutInflater, parent, false)
                return ProductViewHolder(binding, itemClick)
            }
        }
    }
}