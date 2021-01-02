package kg.nurtelecom.sell.core

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class CoreAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<T> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position in items.indices) {
            (holder as Binder<T>).bind(items[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = getViewHolder(parent, viewType)

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    interface Binder<in T> {
        fun bind(item: T)
    }
}