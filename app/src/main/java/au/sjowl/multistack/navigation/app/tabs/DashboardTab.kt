package au.sjowl.multistack.navigation.app.tabs

import au.sjowl.libs.navigation.Tab
import au.sjowl.multistack.navigation.app.Screens

class DashboardTab : Tab(Screens.DASHBOARD) {
    override val screens: ArrayList<String>
        get() = arrayListOf(
            Screens.DASHBOARD,
            Screens.DASHBOARD_ITEM
        )
}