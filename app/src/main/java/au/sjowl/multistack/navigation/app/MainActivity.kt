package au.sjowl.multistack.navigation.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import au.sjowl.libs.navigation.NavigatorProvider
import au.sjowl.libs.navigation.navigator.FragmentNavigator
import au.sjowl.multistack.R
import it.sephiroth.android.library.bottomnavigation.BottomNavigation
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), NavigatorProvider {

    override fun getNavigator() = navigatorInstance

    private val navigatorInstance: FragmentNavigator by lazy {
        TabsFragmentNavigator(this) { tabKey ->
            bottomBar.setSelectedIndex(when (tabKey) {
                Screens.HOME -> 0
                Screens.DASHBOARD -> 1
                else -> throw IllegalArgumentException("no such tab")
            }, false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigatorInstance.goToLastOpenedScreen()

        bottomBar.menuItemSelectionListener = object : BottomNavigation.OnMenuItemSelectionListener {
            override fun onMenuItemReselect(itemId: Int, position: Int, fromUser: Boolean) {
                if (fromUser) navigatorInstance.backTo(screenKeyFromId(itemId))
            }

            override fun onMenuItemSelect(itemId: Int, position: Int, fromUser: Boolean) {
                if (fromUser) navigatorInstance.goToTab(screenKeyFromId(itemId))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        navigatorInstance.onStart()
    }

    override fun onStop() {
        navigatorInstance.onStop()
        super.onStop()
    }

    private var lastTimeBackPressed = System.currentTimeMillis() - 10000

    override fun onBackPressed() {
        val canGoBack = navigatorInstance.goBack()
        if (!canGoBack) {
            if (System.currentTimeMillis() - lastTimeBackPressed > BACK_PRESS_THRESHHOLD_MS) {
                toast("To close app press back again")
                lastTimeBackPressed = System.currentTimeMillis()
            } else {
                navigatorInstance.clearHistory()
                finish()
            }
        }
    }

    private fun screenKeyFromId(id: Int): String {
        return when (id) {
            R.id.navigation_home -> Screens.HOME
            R.id.navigation_dashboard -> Screens.DASHBOARD
            R.id.navigation_notifications -> Screens.HOME
            else -> throw IllegalArgumentException("no such tab")
        }
    }

    companion object {
        private const val BACK_PRESS_THRESHHOLD_MS = 600
    }
}