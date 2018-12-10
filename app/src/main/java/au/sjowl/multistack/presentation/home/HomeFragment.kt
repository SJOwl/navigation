package au.sjowl.multistack.presentation.home

import android.os.Bundle
import android.view.View
import au.sjowl.multistack.R
import au.sjowl.multistack.navigation.app.Screens
import au.sjowl.multistack.presentation.dashboarditem.DashboardItemFragment
import au.sjowl.sjnavigation.BaseNavigationFragment
import au.sjowl.sjnavigation.Screen
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : BaseNavigationFragment(), Screen {

    override val layoutId: Int get() = R.layout.fragment_home

    override val key: String get() = Screens.HOME

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.openDashboardItemButton.setOnClickListener {
            getNavigator().goTo(DashboardItemFragment.getInstance("item 2"))
        }
    }

    companion object {
        fun getInstance() = HomeFragment()
        fun getInstance(arguments: Bundle?, state: Bundle) = HomeFragment().apply {
            this.arguments = arguments
            this.state = state
        }
    }
}