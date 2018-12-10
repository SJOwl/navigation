package au.sjowl.sjnavigation

import au.sjowl.sjnavigation.navigator.FragmentNavigator

interface NavigatorProvider {
    fun getNavigator(): FragmentNavigator
}