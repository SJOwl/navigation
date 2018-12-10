package au.sjowl.multistack.presentation.home

import android.os.Bundle
import android.view.View
import au.sjowl.multistack.R
import au.sjowl.multistack.navigation.app.Screens
import au.sjowl.multistack.presentation.dashboarditem.DashboardItemNavigaitonFragment
import au.sjowl.sjnavigation.base.BaseNavigaitonFragment
import au.sjowl.sjnavigation.lib.NavigatorProvider
import au.sjowl.sjnavigation.lib.Screen
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeNavigaitonFragment : BaseNavigaitonFragment(), Screen {
    override val layoutId: Int get() = R.layout.fragment_home

    override val key: String get() = Screens.HOME

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.openDashboardItemButton.setOnClickListener {
            (activity as NavigatorProvider).getNavigator().goTo(DashboardItemNavigaitonFragment.getInstance("item 2"))
        }
    }

    companion object {
        fun getInstance() = HomeNavigaitonFragment()
        fun getInstance(arguments: Bundle?, state: Bundle) = HomeNavigaitonFragment().apply {
            this.arguments = arguments
            this.state = state
        }
    }
}