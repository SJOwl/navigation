package au.sjowl.sjnavigation.lib.navigator

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import au.sjowl.sjnavigation.base.BaseNavigationFragment
import au.sjowl.sjnavigation.lib.Screen
import au.sjowl.sjnavigation.lib.ScreenState
import au.sjowl.sjnavigation.utils.setProperty
import com.google.gson.Gson

abstract class FragmentNavigator(
    private val activity: FragmentActivity,
    private val onTabSelectListener: ((tabKey: String) -> Unit)
) : TabsNavigator() {

    abstract val homeId: Int
    abstract val homeScreenKey: String

    override fun openNewScreen(screen: Screen) {
        activity.supportFragmentManager.beginTransaction().apply {
            replace(homeId, screen as BaseNavigationFragment)
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
            if (currentTab?.rootScreen != homeScreenKey) {
                goToTab(homeScreenKey)
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
                onTabSelectListener.invoke(tab.rootScreen)
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
        onTabSelectListener.invoke(currentTab?.rootScreen
            ?: throw IllegalStateException("no current tab"))
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