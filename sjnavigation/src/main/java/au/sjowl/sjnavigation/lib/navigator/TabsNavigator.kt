package au.sjowl.sjnavigation.lib.navigator

import au.sjowl.sjnavigation.lib.Tab

abstract class TabsNavigator : Navigator() {
    protected open val tabs = mutableMapOf<String, Tab>()
    protected open var currentTab: Tab? = null
    abstract fun goToTab(screenKey: String)
    abstract fun onStop()
    abstract fun onStart()
    abstract fun clearHistory()
}