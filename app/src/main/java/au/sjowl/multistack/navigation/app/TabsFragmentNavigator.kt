package au.sjowl.multistack.navigation.app

import androidx.fragment.app.FragmentActivity
import au.sjowl.multistack.R
import au.sjowl.multistack.navigation.app.tabs.DashboardTab
import au.sjowl.multistack.navigation.app.tabs.HomeTab
import au.sjowl.multistack.presentation.dashboard.DashboardFragment
import au.sjowl.multistack.presentation.dashboarditem.DashboardItemFragment
import au.sjowl.multistack.presentation.home.HomeFragment
import au.sjowl.sjnavigation.lib.Screen
import au.sjowl.sjnavigation.lib.ScreenState
import au.sjowl.sjnavigation.lib.navigator.FragmentNavigator

class TabsFragmentNavigator(
    activity: FragmentActivity,
    onTabSelectListener: ((tabKey: String) -> Unit)
) : FragmentNavigator(activity, onTabSelectListener) {

    override var currentScreen: Screen = HomeFragment.getInstance()

    override val homeId: Int get() = R.id.fragmentContainer

    override val homeScreenKey: String get() = Screens.HOME

    init {
        tabs.apply {
            put(Screens.HOME, HomeTab())
            put(Screens.DASHBOARD, DashboardTab())
        }
        currentScreen = HomeFragment.getInstance()
        currentTab = tabs[Screens.HOME]
    }

    override fun getScreen(screenState: ScreenState): Screen {
        return when (screenState.screenKey) {
            Screens.HOME -> HomeFragment.getInstance(screenState.arguments, screenState.savedState)
            Screens.DASHBOARD -> DashboardFragment.getInstance(screenState.arguments, screenState.savedState)
            Screens.DASHBOARD_ITEM -> DashboardItemFragment.getInstance(screenState.arguments, screenState.savedState)
            else -> throw IllegalArgumentException("no such screen")
        }
    }
}