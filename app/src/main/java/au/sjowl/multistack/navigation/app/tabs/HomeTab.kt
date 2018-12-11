package au.sjowl.multistack.navigation.app.tabs

import au.sjowl.libs.navigation.Tab
import au.sjowl.multistack.navigation.app.Screens

class HomeTab : Tab(Screens.HOME) {
    override val screens: ArrayList<String>
        get() = arrayListOf(
            Screens.HOME
        )
}