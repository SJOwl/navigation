package au.sjowl.multistack.presentation.dashboard

import android.view.View
import au.sjowl.base.basex.BaseViewHolder
import kotlinx.android.synthetic.main.layout_list_item.view.*

class DashboardItemViewHolder(
    view: View,
    val onClickListener: ((item: DashboardItem) -> Unit)
) : BaseViewHolder<DashboardItem>(view) {
    override fun bind(item: DashboardItem) {
        itemView.text1.text = item.name
        itemView.setOnClickListener { onClickListener.invoke(item) }
    }
}