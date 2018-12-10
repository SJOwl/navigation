package au.sjowl.multistack.presentation.dashboard

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import au.sjowl.multistack.R
import au.sjowl.multistack.navigation.app.Screens
import au.sjowl.multistack.presentation.dashboarditem.DashboardItemNavigaitonFragment
import au.sjowl.sjnavigation.base.BaseNavigaitonFragment
import au.sjowl.sjnavigation.lib.NavigatorProvider
import au.sjowl.sjnavigation.lib.Screen
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardNavigaitonFragment : BaseNavigaitonFragment(), Screen {
    override val layoutId: Int get() = R.layout.fragment_dashboard
    override val key: String get() = Screens.DASHBOARD

    private val adapter by lazy {
        DashboardAdapter { item ->
            (activity as NavigatorProvider).getNavigator().goTo(DashboardItemNavigaitonFragment.getInstance(item.name))
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
        fun getInstance(arguments: Bundle?, state: Bundle) = DashboardNavigaitonFragment().apply {
            this.arguments = arguments
            this.state = state
        }
    }
}