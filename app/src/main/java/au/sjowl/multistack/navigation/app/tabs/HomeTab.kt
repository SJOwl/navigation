package au.sjowl.multistack.navigation.app.tabs

import au.sjowl.multistack.navigation.app.Screens
import au.sjowl.sjnavigation.lib.Tab

class HomeTab : Tab(Screens.HOME) {
    override val screens: ArrayList<String>
        get() = arrayListOf(
            Screens.HOME
        )
}