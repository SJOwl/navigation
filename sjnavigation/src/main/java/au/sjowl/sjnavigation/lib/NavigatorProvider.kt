package au.sjowl.sjnavigation.lib

import au.sjowl.sjnavigation.lib.navigator.TabsNavigator

interface NavigatorProvider {
    fun getNavigator(): TabsNavigator
}