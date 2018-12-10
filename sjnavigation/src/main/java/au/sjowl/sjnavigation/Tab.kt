package au.sjowl.sjnavigation

import java.util.Stack

abstract class Tab(val rootScreen: String) {
    abstract val screens: ArrayList<String>
    val stack = Stack<ScreenState>()
}