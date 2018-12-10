package au.sjowl.base.basex

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<D>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: D)
}