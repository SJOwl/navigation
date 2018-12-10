package au.sjowl.multistack.presentation.dashboard

import android.view.ViewGroup
import au.sjowl.multistack.R
import au.sjowl.sjnavigation.base.BaseRecyclerViewAdapter

class DashboardAdapter(
    val onClickListener: ((item: DashboardItem) -> Unit)
) : BaseRecyclerViewAdapter<DashboardItem, DashboardItemViewHolder>() {
    override fun getViewHolderLayoutId(viewType: Int): Int = R.layout.layout_list_item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardItemViewHolder {
        return DashboardItemViewHolder(inflate(parent, viewType), onClickListener)
    }
}