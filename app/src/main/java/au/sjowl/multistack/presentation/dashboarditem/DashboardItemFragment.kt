package au.sjowl.multistack.presentation.dashboarditem

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import au.sjowl.multistack.R
import au.sjowl.multistack.navigation.app.Screens
import au.sjowl.sjnavigation.base.BaseNavigationFragment
import au.sjowl.sjnavigation.lib.Screen
import kotlinx.android.synthetic.main.fragment_dashboard_item.view.*

class DashboardItemFragment : BaseNavigationFragment(), Screen {
    override val layoutId: Int get() = R.layout.fragment_dashboard_item
    override val key: String get() = Screens.DASHBOARD_ITEM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.textView.text = arguments?.get(KEY_ID).toString()
    }

    companion object {
        const val KEY_ID = "id"
        fun getInstance(id: String) = DashboardItemFragment().apply {
            arguments = bundleOf(KEY_ID to id)
        }

        fun getInstance(arguments: Bundle?, state: Bundle) = DashboardItemFragment().apply {
            this.arguments = arguments
            this.state = state
        }
    }
}