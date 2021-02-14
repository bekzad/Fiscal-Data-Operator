package kg.nurtelecom.sell.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kg.nurtelecom.data.sell.CatalogResult
import kg.nurtelecom.data.sell.Products
import kg.nurtelecom.sell.core.ItemClickListener
import kg.nurtelecom.sell.databinding.ProductCategoryHeaderBinding
import kg.nurtelecom.sell.databinding.ProductCategoryItemBinding
import kg.nurtelecom.sell.utils.roundUp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductCategoryAdapter(private val clickListener: ItemClickListener) :
    ListAdapter<ProductsItem, RecyclerView.ViewHolder>(ProductCategoryDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(catalog: List<CatalogResult>?, sortedList: List<Products>? = null) {
        adapterScope.launch {
            if (!sortedList.isNullOrEmpty()) {
                submitSortedProducts(sortedList)
            } else {
                val items = when (catalog) {
                    null -> listOf(ProductsItem.Header(""))
                    else -> {
                        val products = ArrayList<ProductsItem>()
                        for (i in catalog) {
                            products.add(ProductsItem.Header(i.productCatalog.name))
                            for (v in i.products) {
                                products.add(ProductsItem.ProductItem(v))
                            }
                        }
                        products
                    }
                }
                submitProducts(items)
            }
        }
    }

    private suspend fun submitProducts(items: List<ProductsItem>?) {
        withContext(Dispatchers.Main) {
            submitList(items)
        }
    }

    private suspend fun submitSortedProducts(sortedList: List<Products>) {
        val mList = ArrayList<ProductsItem>()
        for (i in sortedList) {
            mList.add(ProductsItem.ProductItem(i))
        }
        submitProducts(mList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> ProductHeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ProductsViewHolder.from(parent)
            else -> throw ClassCastException("Unknown type viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProductHeaderViewHolder -> {
                val item = getItem(position) as ProductsItem.Header
                holder.bind(item.name)
            }
            is ProductsViewHolder -> {
                val item = getItem(position) as ProductsItem.ProductItem
                holder.bind(item.product, clickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ProductsItem.Header -> ITEM_VIEW_TYPE_HEADER
            is ProductsItem.ProductItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    companion object {
        private const val ITEM_VIEW_TYPE_HEADER = 0
        private const val ITEM_VIEW_TYPE_ITEM = 1
    }
}

class ProductsViewHolder(private val binding: ProductCategoryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Products, clickListener: ItemClickListener) {
        binding.apply {
            root.setOnClickListener {
                clickListener.transferData(product)
            }
            tvProductName.text = product.name
            val formattedPrice = "${product.price.roundUp()} <u>с</u>"
            tvProductPrice.text =
                HtmlCompat.fromHtml(formattedPrice, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }

    companion object {
        fun from(parent: ViewGroup): ProductsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ProductCategoryItemBinding.inflate(layoutInflater, parent, false)
            return ProductsViewHolder(binding)
        }
    }
}

class ProductHeaderViewHolder(private val binding: ProductCategoryHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(name: String) {
        binding.tvHeader.text = name
    }

    companion object {
        fun from(parent: ViewGroup): ProductHeaderViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ProductCategoryHeaderBinding.inflate(layoutInflater, parent, false)
            return ProductHeaderViewHolder(binding)
        }
    }
}

class ProductCategoryDiffCallback : DiffUtil.ItemCallback<ProductsItem>() {
    override fun areItemsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean {
        return oldItem == newItem
    }
}

sealed class ProductsItem {
    data class ProductItem(val product: Products) : ProductsItem() {
        override val id: Long = product.id
    }

    data class Header(val name: String) : ProductsItem() {
        override val id: Long = name.hashCode().toLong()
    }

    abstract val id: Long
}
