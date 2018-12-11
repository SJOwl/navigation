package au.sjowl.libs.navigation

import au.sjowl.libs.navigation.navigator.FragmentNavigator

interface NavigatorProvider {
    fun getNavigator(): FragmentNavigator
}