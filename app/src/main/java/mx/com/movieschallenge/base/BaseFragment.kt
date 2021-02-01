package mx.com.movieschallenge.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import mx.com.movieschallenge.ui.adapter.MoviesAdapter

abstract class BaseFragment : Fragment() {

    abstract fun getLayoutView(): Int
    abstract fun initView()
    abstract fun attachObservers()


    val isLandScape by lazy {
        context?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE
    }
    protected val moviesAdapter = MoviesAdapter(mutableListOf())
    protected var moviesLayoutManager: GridLayoutManager? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutView(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        moviesLayoutManager = GridLayoutManager(requireContext(),
                getColumnsByOrientation(isLandScape))
        initView()
        attachObservers()
    }

    /**
     * Method to get recyclerview's columns according to the current orientation
     */
    protected fun getColumnsByOrientation(isLandScape: Boolean) = if (isLandScape) RECYCLER_VIEW_SPAN_COUNT_LANDSCAPE
    else RECYCLER_VIEW_SPAN_COUNT_PORTRAIT

    companion object {
        const val RECYCLER_VIEW_SPAN_COUNT_PORTRAIT = 2
        const val RECYCLER_VIEW_SPAN_COUNT_LANDSCAPE = 3
        const val SPACE_ITEM_DECORATION = 12
    }
}