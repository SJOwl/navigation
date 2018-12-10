package au.sjowl.sjnavigation.lib

import java.util.Stack

abstract class Tab(val key: String) {
    abstract val rootScreen: String
    abstract val screens: ArrayList<String>
    val stack = Stack<ScreenState>()
}