package au.sjowl.sjnavigation.base

import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import au.sjowl.sjnavigation.lib.NavigatorProvider
import au.sjowl.sjnavigation.lib.Screen
import au.sjowl.sjnavigation.lib.navigator.TabsNavigator

abstract class BaseNavigaitonFragment :
    Fragment(), Screen, NavigatorProvider {

    protected abstract val layoutId: Int

    override val screenArguments: Bundle? get() = arguments
    override var state: Bundle = bundleOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(layoutId, container, false)
    }

    override fun onResume() {
        super.onResume()
        state.let { bundle ->
            val vState = bundle.getSparseParcelableArray<Parcelable>(KEY_STATE)
            vState?.let { array -> view?.restoreHierarchyState(array) }
        }
    }

    override fun onPause() {
        val vState = SparseArray<Parcelable>()
        view?.saveHierarchyState(vState)
        state.putSparseParcelableArray(KEY_STATE, vState)
        super.onPause()
    }

    override fun getNavigator(): TabsNavigator = (activity as NavigatorProvider).getNavigator()

    companion object {
        private const val KEY_STATE = "key_state"
    }
}