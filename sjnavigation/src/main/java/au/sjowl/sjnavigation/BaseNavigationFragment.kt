package au.sjowl.sjnavigation

import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

abstract class BaseNavigationFragment :
    Fragment(),
    Screen,
    NavigatorProvider {

    protected abstract val layoutId: Int

    override val screenArguments: Bundle? get() = arguments
    override var state: Bundle = bundleOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(layoutId, container, false)
    }

    override fun onResume() {
        super.onResume()
        restoreState()
    }

    override fun onPause() {
        saveState()
        super.onPause()
    }

    override fun getNavigator() = (activity as NavigatorProvider).getNavigator()

    fun saveState() {
        val vState = SparseArray<Parcelable>()
        view?.saveHierarchyState(vState)
        state.putSparseParcelableArray(KEY_STATE, vState)
    }

    fun restoreState() {
        state.let { bundle ->
            val vState = bundle.getSparseParcelableArray<Parcelable>(KEY_STATE)
            vState?.let { array -> view?.restoreHierarchyState(array) }
        }
    }

    companion object {
        private const val KEY_STATE = "key_state"
    }
}