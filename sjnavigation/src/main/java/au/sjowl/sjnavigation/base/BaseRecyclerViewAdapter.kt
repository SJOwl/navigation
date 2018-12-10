package au.sjowl.sjnavigation.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * T - ViewHolder class
 * D - model data class
 */
abstract class BaseRecyclerViewAdapter<D : Any, T : BaseViewHolder<D>> : RecyclerView.Adapter<T>() {

    var items: List<D> = ArrayList() /* todo use diffutils */
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = items.size

    abstract fun getViewHolderLayoutId(viewType: Int): Int

    fun inflate(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(parent.context).inflate(getViewHolderLayoutId(viewType), parent, false)
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        onBindViewHolder(holder, items[position])
    }

    open fun onBindViewHolder(holder: T, item: D) = holder.bind(item)
}