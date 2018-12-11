package au.sjowl.libs.navigation.navigator

import au.sjowl.libs.navigation.Tab

abstract class TabsNavigator : Navigator() {
    protected open val tabs = mutableMapOf<String, Tab>()
    protected open var currentTab: Tab? = null
    abstract fun goToTab(screenKey: String)
}