package mx.com.movieschallenge.ui.favorite

import android.content.Context
import android.content.res.Configuration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.favorites_movies_fragment.*
import mx.com.movieschallenge.R
import mx.com.movieschallenge.base.BaseFragment
import mx.com.movieschallenge.domain.model.Movie
import mx.com.movieschallenge.ui.adapter.MoviesAdapter
import mx.com.movieschallenge.ui.listener.MovieCommunication
import mx.com.movieschallenge.ui.model.FavoriteMoviesViewState
import mx.com.movieschallenge.utils.extensions.gone
import mx.com.movieschallenge.utils.extensions.observe
import mx.com.movieschallenge.utils.extensions.snack
import mx.com.movieschallenge.utils.extensions.visible
import mx.com.movieschallenge.utils.recyclerview.SpacesItemDecoration
import org.koin.android.viewmodel.ext.android.viewModel

class FavoritesMoviesFragment : BaseFragment() {

    private val viewModel: FavoritesMoviesViewModel by viewModel()

    lateinit var communication: MovieCommunication

    override fun getLayoutView(): Int = R.layout.favorites_movies_fragment

    override fun initView() {
        initAdapterManager()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        communication = context as MovieCommunication
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val isLandscape = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
        rvFavoriteMovies?.apply {
            moviesLayoutManager?.spanCount = getColumnsByOrientation(isLandscape)
            adapter?.notifyDataSetChanged()
        }
    }

    override fun attachObservers() {
        viewModel.apply {
            getFavoritesMovies()
            observe(favoriteMoviesViewState, ::handleFavoriteViewState)
        }
    }

    private fun handleFavoriteViewState(viewState: FavoriteMoviesViewState) {
        when (viewState) {
            is FavoriteMoviesViewState.OnSuccessAddFavorite -> snack(R.string.message_success_add_favorites)
            is FavoriteMoviesViewState.OnSuccessRemoveFavorite -> snack(R.string.message_success_remove_favorites)
            is FavoriteMoviesViewState.OnResultFavorites -> showFavoritesMovies(viewState.movies)
            is FavoriteMoviesViewState.OnEmptyFavorites -> showEmptyFavorites()
        }
    }

    private fun showEmptyFavorites() {
        rvFavoriteMovies.gone()
        tvEmptyFavoritesMovies.visible()
    }

    private fun showFavoritesMovies(movies: List<Movie>) {
        moviesAdapter.updateMovies(movies)
        rvFavoriteMovies.visible()
        tvEmptyFavoritesMovies.gone()
    }

    private fun initAdapterManager() {
        rvFavoriteMovies?.apply {
            layoutManager = moviesLayoutManager ?:GridLayoutManager(requireContext(),
                    getColumnsByOrientation(isLandScape))
            addItemDecoration(SpacesItemDecoration(SPACE_ITEM_DECORATION))
            adapter = moviesAdapter
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
}