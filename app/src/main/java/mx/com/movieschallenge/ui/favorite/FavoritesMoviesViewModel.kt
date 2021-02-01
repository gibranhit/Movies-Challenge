package mx.com.movieschallenge.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mx.com.movieschallenge.base.BaseViewModel
import mx.com.movieschallenge.domain.model.Movie
import mx.com.movieschallenge.domain.usecase.GetFavoritesMoviesUseCase
import mx.com.movieschallenge.ui.model.FavoriteMoviesViewState

class FavoritesMoviesViewModel(private val useCase: GetFavoritesMoviesUseCase) : BaseViewModel() {

    private var _favoriteViewState = MutableLiveData<FavoriteMoviesViewState>()

    val favoriteMoviesViewState: LiveData<FavoriteMoviesViewState>
        get() = _favoriteViewState

    init {
        getFavoritesMovies()
    }

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

    private fun handleFavoritesListResult(movies: List<Movie>) {
        if (movies.isNullOrEmpty()) {
            _favoriteViewState.postValue(FavoriteMoviesViewState.OnEmptyFavorites)

        } else {
            _favoriteViewState.postValue(FavoriteMoviesViewState.OnResultFavorites(movies))
        }
    }

}