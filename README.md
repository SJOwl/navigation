# navigation
[ ![Download](https://api.bintray.com/packages/sjowl/navigation/navigation/images/download.svg) ](https://bintray.com/sjowl/navigation/navigation/_latestVersion)

Android navigation for single activity apps with bottombar

```gradle
implementation "au.sj.sjnavigation:navigation:0.1.0"
```
**Features**
Navigate between tabs: from one tab open fragment from another tab and second tab will be selected.
To navigate from fragment use
```kotlin
getNavigator().goTo(MyFragment.getInstance(myArguments)),
where myArguments - bundle with arguments

fun goBack(): Boolean
fun goToLastOpenedScreen()
fun goTo(screen: Screen)
fun backTo(screenKey: String)
fun goToTab(screenKey: String)
```

**Usage**
1. Define **Screens** object
```kotlin
object Screens {
    /** tabs */
    const val HOME = "HOME"
    const val DASHBOARD = "DASHBOARD"

    /** other */
    const val DASHBOARD_ITEM = "DASHBOARD_ITEM"
}
```
2. For each tab define class
```kotlin
class HomeTab : Tab(Screens.HOME) {
    override val screens: ArrayList<String>
        get() = arrayListOf(
            Screens.HOME
        )
}

class DashboardTab : Tab(Screens.DASHBOARD) {
    override val screens: ArrayList<String>
        get() = arrayListOf(
            Screens.DASHBOARD,
            Screens.DASHBOARD_ITEM
        )
}
```
3. Implement **Host activity**
```kotlin
class MainActivity : AppCompatActivity(), NavigatorProvider {

    override fun getNavigator(): TabsNavigator = navigatorInstance

    private val navigatorInstance: TabsNavigator by lazy {
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
```
4. Implement **TabsFragmentNavigator**
```kotlin
class TabsFragmentNavigator(
    activity: FragmentActivity,
    onTabSelectListener: ((tabKey: String) -> Unit)
) : FragmentNavigator(activity, onTabSelectListener) {

    override var currentScreen: Screen = HomeFragment.getInstance()

    override val homeId: Int get() = R.id.fragmentContainer

    override val homeScreenKey: String get() = Screens.HOME

    init {
        tabs.apply {
            put(Screens.HOME, HomeTab())
            put(Screens.DASHBOARD, DashboardTab())
        }
        currentScreen = HomeFragment.getInstance()
        currentTab = tabs[Screens.HOME]
    }

    override fun getScreen(screenState: ScreenState): Screen {
        return when (screenState.screenKey) {
            Screens.HOME -> HomeFragment.getInstance(screenState.arguments, screenState.savedState)
            Screens.DASHBOARD -> DashboardFragment.getInstance(screenState.arguments, screenState.savedState)
            Screens.DASHBOARD_ITEM -> DashboardItemFragment.getInstance(screenState.arguments, screenState.savedState)
            else -> throw IllegalArgumentException("no such screen")
        }
    }
}
```
5. For each fragment implement **BaseNavigationFragment(), Screen**
```kotlin
class DashboardFragment : BaseNavigationFragment(), Screen {
    override val layoutId: Int get() = R.layout.fragment_dashboard
    override val key: String get() = Screens.DASHBOARD

    private val adapter by lazy {
        DashboardAdapter { item ->
            getNavigator().goTo(DashboardItemFragment.getInstance(item.name))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = arrayListOf<DashboardItem>().apply {
            (0..100).forEach { add(DashboardItem("item $it")) }
        }
        recyclerView.adapter = adapter
        adapter.items = items
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    companion object {
        fun getInstance(arguments: Bundle?, state: Bundle) = DashboardFragment().apply {
            this.arguments = arguments
            this.state = state
        }
    }
}
```

