package mx.com.movieschallenge.ui.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mx.com.movieschallenge.base.BaseViewModel
import mx.com.movieschallenge.domain.model.Movie
import mx.com.movieschallenge.domain.usecase.GetFavoritesMoviesUseCase
import mx.com.movieschallenge.ui.model.FavoriteMoviesViewState

class FavoritesMoviesViewModel(private val useCase: GetFavoritesMoviesUseCase) : BaseViewModel() {

    //Private fields
    private var _favoriteViewState = MutableLiveData<FavoriteMoviesViewState>()

    //Public fields
    val favoriteMoviesViewState: LiveData<FavoriteMoviesViewState>
        get() = _favoriteViewState

    init {
        //getFavoritesMovies()
    }

    /**
     * Live data of favorites movies in this case the fragment will be attached to this live data variable
     * to observe database changes and update the list of favorite movies in the UI.
     */

    fun getFavoritesMovies(){
        main {
            runCatching {
                useCase.getFavorites()
            }.onSuccess {
                handleFavoritesListResult(it)
            }.onFailure {
                _favoriteViewState.postValue(FavoriteMoviesViewState.OnEmptyFavorites)
            }
        }
    }

    /**
     * Update favorite movies status
     */
    fun updateFavorite(id: Long, isFavorite: Boolean) {
        main {
            runCatching {
                useCase.updateFavoriteState(id, isFavorite)
            }.onSuccess {
                getFavoritesMovies()
                _favoriteViewState.postValue(getFavoriteViewStateWhenUpdate(isFavorite))
            }
        }
    }

    /**
     * Handle list of movies and notify to the UI.
     */
    private fun handleFavoritesListResult(movies: List<Movie>) {
        if (movies.isNullOrEmpty()) {
            _favoriteViewState.postValue(FavoriteMoviesViewState.OnEmptyFavorites)

        } else {
            _favoriteViewState.postValue(FavoriteMoviesViewState.OnResultFavorites(movies))
        }
    }

}