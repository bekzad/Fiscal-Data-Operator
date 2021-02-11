package kg.nurtelecom.sell.ui.fragment.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kg.nurtelecom.core.extension.formatForDecoratorDateTimeDefaults
import kg.nurtelecom.core.extension.formatForLocalDateTimeDefaults
import kg.nurtelecom.core.extension.roundOff
import kg.nurtelecom.data.enums.OperationType
import kg.nurtelecom.data.history.Content
import kg.nurtelecom.sell.core.ItemClickListener
import kg.nurtelecom.sell.databinding.ProductCategoryHeaderBinding
import kg.nurtelecom.ui.databinding.DetailViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HistoryAdapter(private val itemClick: ItemClickListener) :
        ListAdapter<ChecksItem, RecyclerView.ViewHolder>(CheckOperationTypeDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(content: List<Content>?, sortedList: List<Content>? = null) {
        val uniqueContent = content?.distinctBy { dateFormat(it.createdAt) }
        adapterScope.launch {
            if (!sortedList.isNullOrEmpty()) {
                submitSortedProducts(sortedList)
            } else {
                val items = when (content) {
                    null -> listOf(ChecksItem.Header(""))
                    else -> {
                        val checks = ArrayList<ChecksItem>()
                        if (uniqueContent != null) {
                            for (i in uniqueContent) {
                                val date = dateFormat(i.createdAt)
                                checks.add(ChecksItem.Header(date))
                                for (j in content) {
                                    if(date == dateFormat(j.createdAt)) {
                                        checks.add(ChecksItem.CheckItem(j))
                                    }
                                }
                            }
                        }
                        checks
                    }
                }
                submitProducts(items)
            }
        }
    }

    private fun dateFormat(date: String): String {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSS", Locale.getDefault()).parse(date).formatForDecoratorDateTimeDefaults()
    }

    private suspend fun submitProducts(items: List<ChecksItem>?) {
        withContext(Dispatchers.Main) {
            submitList(items)
        }
    }

    private suspend fun submitSortedProducts(sortedList: List<Content>?) {
        val mList = ArrayList<ChecksItem>()
        if (sortedList != null) {
            for (i in sortedList) {
                mList.add(ChecksItem.CheckItem(i))
            }
        }
        submitProducts(mList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> CheckHeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> HistoryViewHolder.from(parent, itemClick)
            else -> throw ClassCastException("Unknown type viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CheckHeaderViewHolder -> {
                val item = getItem(position) as ChecksItem.Header
                holder.bind(item.name)
            }
            is HistoryViewHolder -> {
                val item = getItem(position) as ChecksItem.CheckItem
                holder.bind(item.product)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ChecksItem.Header -> ITEM_VIEW_TYPE_HEADER
            is ChecksItem.CheckItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    companion object {
        private const val ITEM_VIEW_TYPE_HEADER = 0
        private const val ITEM_VIEW_TYPE_ITEM = 1
    }
}

class HistoryViewHolder(private val binding: DetailViewBinding, private val itemClick: ItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {

    fun bind(content: Content) {
        binding.apply {
            tvTitle.text = OperationType.valueOf(content.operationType).type
            tvCounter.text = "#${content.indexNum}"
            tvTimestamp.text = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSS").parse(content.createdAt).formatForLocalDateTimeDefaults()
            tvAmount.text = "${content.total.roundOff(2)} с"
        }

        itemView.setOnClickListener {
            itemClick.onItemClick(content.id.toInt())
        }
    }

    companion object {
        fun from(parent: ViewGroup, itemClick: ItemClickListener): HistoryViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = DetailViewBinding.inflate(layoutInflater, parent, false)
            return HistoryViewHolder(binding, itemClick)
        }
    }
}

class CheckHeaderViewHolder(private val binding: ProductCategoryHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

    fun bind(name: String) {
        binding.tvHeader.text = name
    }

    companion object {
        fun from(parent: ViewGroup): CheckHeaderViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ProductCategoryHeaderBinding.inflate(layoutInflater, parent, false)
            return CheckHeaderViewHolder(binding)
        }
    }
}

class CheckOperationTypeDiffCallback : DiffUtil.ItemCallback<ChecksItem>() {
    override fun areItemsTheSame(oldItem: ChecksItem, newItem: ChecksItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ChecksItem, newItem: ChecksItem): Boolean {
        return oldItem == newItem
    }
}

sealed class ChecksItem {
    data class CheckItem(val product: Content) : ChecksItem() {
        override val id: Long = product.id
    }

    data class Header(val name: String) : ChecksItem() {
        override val id: Long = name.hashCode().toLong()
    }

    abstract val id: Long
}