package mx.com.movieschallenge.ui.popular

import android.content.Context
import android.content.res.Configuration
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.transition.platform.MaterialElevationScale
import kotlinx.android.synthetic.main.popular_movies_fragment.*
import mx.com.movieschallenge.R
import mx.com.movieschallenge.base.BaseFragment
import mx.com.movieschallenge.domain.model.Movie
import mx.com.movieschallenge.ui.listener.MovieCommunication
import mx.com.movieschallenge.ui.model.FavoriteMoviesViewState
import mx.com.movieschallenge.ui.model.PopularMoviesViewState
import mx.com.movieschallenge.utils.extensions.*
import mx.com.movieschallenge.utils.recyclerview.InfiniteScrollProvider
import mx.com.movieschallenge.utils.recyclerview.SpacesItemDecoration
import org.koin.android.viewmodel.ext.android.viewModel

class PopularMoviesFragment : BaseFragment() {

    private val viewModel: PopularMoviesViewModel by viewModel()

    lateinit var communication: MovieCommunication

    override fun getLayoutView(): Int = R.layout.popular_movies_fragment

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val isLandscape = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
        rvPopularMovies.apply {
            moviesLayoutManager?.spanCount = getColumnsByOrientation(isLandscape)
            adapter?.notifyDataSetChanged()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        communication = context as MovieCommunication
    }

    override fun initView() {
        initAdapterManager()
    }

    override fun attachObservers() {
        viewModel.apply {
            observe(popularMovies, ::addNewMovies)
            observe(popularViewState, ::handlePopularViewState)
            observe(favoriteMoviesViewState, ::handleFavoriteViewState)
        }
    }

    private fun addNewMovies(movies: List<Movie>){
        moviesAdapter.addNewMovies(movies)
    }

    private fun initAdapterManager() {
        rvPopularMovies?.apply {
            layoutManager = moviesLayoutManager ?:GridLayoutManager(requireContext(),
                    getColumnsByOrientation(isLandScape))
            adapter = moviesAdapter
            addItemDecoration(SpacesItemDecoration(SPACE_ITEM_DECORATION))
            addScrollListener()
            setHasFixedSize(true)
            handleItemClickListener()
        }
    }

    private fun handleItemClickListener() {
        moviesAdapter.apply {
            setItemClickListener { poster, title, ranking, movie ->
                communication.selectMovie(poster, title, ranking, movie)
            }

            setUpdateListener {
                it.run {
                    val newValue = !isFavorite
                    isFavorite = newValue
                    notifyDataSetChanged()
                    viewModel.updateFavorite(id, newValue)
                }
            }
        }
    }

    private fun addScrollListener() {
        InfiniteScrollProvider().attach(rvPopularMovies, object : InfiniteScrollProvider.OnLoadMoreListener {
            override fun onLoadMore() {
                viewModel.fetchPopularMovies()
            }
        })
    }

    private fun handlePopularViewState(viewState: PopularMoviesViewState) {
        when (viewState) {
            is PopularMoviesViewState.OnSuccessFetch -> loadingAnimation.gone()
            is PopularMoviesViewState.OnLoading -> loadingAnimation.visible()
            is PopularMoviesViewState.OnMaxPagesReached -> snack(R.string.message_max_pages_reached)
            is PopularMoviesViewState.OnError -> {
                loadingAnimation.gone()
                if (requireActivity().hasNetworkConnection())
                    snack(R.string.message_error_fetching)
                else
                    showAlert(R.string.error_verify_network_connection){
                        viewModel.fetchPopularMovies()
                    }
            }
        }
    }

    private fun handleFavoriteViewState(viewState: FavoriteMoviesViewState) {
        when (viewState) {
            is FavoriteMoviesViewState.OnSuccessAddFavorite -> snack(R.string.message_success_add_favorites)
            else -> snack(R.string.message_success_remove_favorites)
        }
    }

}