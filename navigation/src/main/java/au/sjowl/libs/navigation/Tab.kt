package au.sjowl.libs.navigation

import java.util.Stack

abstract class Tab(val rootScreen: String) {
    abstract val screens: ArrayList<String>
    val stack = Stack<ScreenState>()
}