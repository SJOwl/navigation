package au.sjowl.multistack.navigation.app.tabs

import au.sjowl.multistack.navigation.app.Screens
import au.sjowl.sjnavigation.Tab

class DashboardTab : Tab(Screens.DASHBOARD) {
    override val screens: ArrayList<String>
        get() = arrayListOf(
            Screens.DASHBOARD,
            Screens.DASHBOARD_ITEM
        )
}