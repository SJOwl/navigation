package au.sjowl.sjnavigation.lib

import au.sjowl.sjnavigation.lib.navigator.FragmentNavigator

interface NavigatorProvider {
    fun getNavigator(): FragmentNavigator
}