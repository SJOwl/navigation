package au.sjowl.libs.navigation

import android.os.Bundle

data class ScreenState(
    val screenKey: String,
    val arguments: Bundle?,
    val savedState: Bundle
) {
    companion object {
        fun fromScreen(screen: Screen) = ScreenState(
            screen.key,
            screen.screenArguments,
            screen.state
        )
    }
}