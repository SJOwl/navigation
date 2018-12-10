package au.sjowl.sjnavigation.navigator

import au.sjowl.sjnavigation.Tab

abstract class TabsNavigator : Navigator() {
    protected open val tabs = mutableMapOf<String, Tab>()
    protected open var currentTab: Tab? = null
    abstract fun goToTab(screenKey: String)
}