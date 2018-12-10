package au.sjowl.sjnavigation.lib.navigator

import au.sjowl.sjnavigation.lib.Screen
import au.sjowl.sjnavigation.lib.ScreenState

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
}