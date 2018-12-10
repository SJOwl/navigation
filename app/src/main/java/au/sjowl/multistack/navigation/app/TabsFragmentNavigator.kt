package au.sjowl.multistack.navigation.app

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import au.sjowl.multistack.R
import au.sjowl.multistack.navigation.app.tabs.DashboardTab
import au.sjowl.multistack.navigation.app.tabs.HomeTab
import au.sjowl.multistack.presentation.dashboard.DashboardNavigaitonFragment
import au.sjowl.multistack.presentation.dashboarditem.DashboardItemNavigaitonFragment
import au.sjowl.multistack.presentation.home.HomeNavigaitonFragment
import au.sjowl.sjnavigation.base.BaseNavigaitonFragment
import au.sjowl.sjnavigation.lib.Screen
import au.sjowl.sjnavigation.lib.ScreenState
import au.sjowl.sjnavigation.lib.navigator.TabsNavigator
import au.sjowl.sjnavigation.utils.setProperty
import com.google.gson.Gson

class TabsFragmentNavigator(
    private val activity: FragmentActivity,
    private val onTabSelectListener: ((tabKey: String) -> Unit)
) : TabsNavigator() {

    override var currentScreen: Screen = HomeNavigaitonFragment.getInstance()

    init {
        tabs.apply {
            put(Screens.HOME, HomeTab())
            put(Screens.DASHBOARD, DashboardTab())
        }
        currentScreen = HomeNavigaitonFragment.getInstance()
        currentTab = tabs[Screens.HOME]
    }

    override fun getScreen(screenState: ScreenState): Screen {
        return when (screenState.screenKey) {
            Screens.HOME -> HomeNavigaitonFragment.getInstance(screenState.arguments, screenState.savedState)
            Screens.DASHBOARD -> DashboardNavigaitonFragment.getInstance(screenState.arguments, screenState.savedState)
            Screens.DASHBOARD_ITEM -> DashboardItemNavigaitonFragment.getInstance(screenState.arguments, screenState.savedState)
            else -> throw IllegalArgumentException("no such screen")
        }
    }

    override fun openNewScreen(screen: Screen) {
        activity.supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, screen as BaseNavigaitonFragment)
            commit()
        }
    }

    override fun goBack(): Boolean {
        val stack = currentTab?.stack
        return if (stack != null && stack.size > 1) {
            stack.pop()
            currentScreen = getScreen(stack.peek())
            openNewScreen(currentScreen)
            true
        } else {
            if (currentTab?.key != Screens.HOME) {
                goToTab(Screens.HOME)
                true
            } else
                false
        }
    }

    override fun goTo(screen: Screen) {
        // deeplinking - restore backstack for this screen:
        // A1 -> B2 back -> B1 back -> A1
        tabs.values.forEach { tab ->
            if (tab.screens.contains(screen.key)) {
                currentTab = tab
                currentScreen = screen
                if (!tab.stack.empty() && tab.stack.peek().screenKey == screen.key) {
                    tab.stack.pop()
                }
                tab.stack.push(ScreenState.fromScreen(screen))
                onTabSelectListener.invoke(tab.key)
                return@forEach
            }
        }
        openNewScreen(screen)
    }

    override fun backTo(screenKey: String) {
        var cur: ScreenState
        currentTab = tabs[screenKey]?.apply {
            do {
                cur = stack.pop()
                currentScreen = getScreen(cur)
            } while (!stack.empty() && cur.screenKey != rootScreen)
            if (stack.empty()) {
                currentScreen = getScreen(stack.push(ScreenState(screenKey, bundleOf(), bundleOf())))
            }
            openNewScreen(currentScreen)
        }
    }

    override fun goToTab(screenKey: String) {
        currentTab = tabs[screenKey]?.apply {
            currentScreen = if (stack.isEmpty()) {
                getScreen(stack.push(ScreenState(screenKey, bundleOf(), bundleOf())))
            } else {
                getScreen(stack.peek())
            }
            openNewScreen(currentScreen)
        }
        onTabSelectListener.invoke(currentTab?.key ?: throw IllegalStateException("no current tab"))
    }

    override fun onStart() {
        activity.applicationContext.run {
            // TODO: load from prefs
            /*val gson = Gson()
            val statesProperty = getProperty(KEY_STATES, gson.toJson(tabs))
            val newTabs = gson.fromJson(statesProperty, mutableMapOf<String, Tab>()::class.java)
            tabs.clear()
            tabs.putAll(newTabs)*/
        }
    }

    override fun onStop() {
        // TODO: save to prefs
        activity.applicationContext.run {
            setProperty(KEY_STATES, Gson().toJson(tabs))
        }
    }

    override fun clearHistory() {
        tabs.values.forEach { tab -> tab.stack.clear() }
    }

    companion object {
        private const val KEY_STATES = "key_states"
    }
}