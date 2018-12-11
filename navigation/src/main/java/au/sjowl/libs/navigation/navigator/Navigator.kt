package au.sjowl.libs.navigation.navigator

import au.sjowl.libs.navigation.Screen
import au.sjowl.libs.navigation.ScreenState

abstract class Navigator {
    protected abstract fun getScreen(screenState: ScreenState): Screen
    protected abstract fun openNewScreen(screen: Screen)
    protected abstract var currentScreen: Screen

    abstract fun goBack(): Boolean
    open fun goToLastOpenedScreen() {
        openNewScreen(currentScreen)
    }

    abstract fun goTo(screen: Screen)
    abstract fun backTo(screenKey: String)
    abstract fun onStop()
    abstract fun onStart()
    abstract fun clearHistory()
}